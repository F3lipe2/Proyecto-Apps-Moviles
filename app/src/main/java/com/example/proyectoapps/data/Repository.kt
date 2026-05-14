package com.example.proyectoapps.data

import android.util.Log
import com.example.proyectoapps.data.local.Asistencia
import com.example.proyectoapps.data.local.Curso
import com.example.proyectoapps.data.local.Usuario
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.time.LocalDate

class AppRepository {
    private val db = FirebaseFirestore.getInstance()

    private fun formatCode(code: String) = code.trim().uppercase()

    suspend fun getUsuario(uid: String): Usuario? {
        return try {
            val doc = db.collection("users").document(uid.trim()).get().await()
            if (!doc.exists()) {
                Log.w("AppRepository", "El documento del usuario $uid no existe en la colección 'users'")
                return null
            }
            doc.toObject(Usuario::class.java)?.copy(id = doc.id)
        } catch (e: Exception) {
            Log.e("AppRepository", "Error al obtener usuario $uid: ${e.message}")
            null
        }
    }

    suspend fun crearCurso(curso: Curso): Boolean {
        return try {
            val normalizedCode = formatCode(curso.codigo)
            val cursoFinal = curso.copy(codigo = normalizedCode)
            db.collection("cursos").document(normalizedCode).set(cursoFinal).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getCursosPorProfesor(idProfesor: String): List<Curso> {
        return try {
            val snapshot = db.collection("cursos")
                .whereEqualTo("idProfesor", idProfesor)
                .get().await()
            snapshot.toObjects(Curso::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getCursoPorCodigo(codigo: String): Curso? {
        return try {
            val doc = db.collection("cursos").document(formatCode(codigo)).get().await()
            doc.toObject(Curso::class.java)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun registrarAsistencia(idEstudiante: String, codigoCurso: String): Boolean {
        return try {
            val uid = idEstudiante.trim()
            val code = formatCode(codigoCurso)
            val fechaActual = LocalDate.now().toString()
            
            Log.d("AppRepository", "Registrando asistencia: Estudiante=$uid, Curso=$code, Fecha=$fechaActual")
            
            // 1. Registrar asistencia
            val asistencia = Asistencia(
                idEstudiante = uid,
                codigoCurso = code,
                fecha = fechaActual,
                presente = true
            )
            val docId = "${uid}_${code}_$fechaActual"
            db.collection("asistencias").document(docId).set(asistencia).await()
            Log.d("AppRepository", "Documento de asistencia creado: $docId")
            
            // 2. Inscripción forzada para que aparezca en la vista del profesor
            // Intentamos obtener el perfil, si falla creamos uno básico para no bloquear la vista
            var user = getUsuario(uid)
            if (user == null) {
                Log.w("AppRepository", "Usando perfil temporal para $uid")
                user = Usuario(id = uid, nombre = "Cargando nombre...", rol = "estudiante")
            }
            
            db.collection("cursos").document(code)
                .collection("estudiantes").document(uid)
                .set(user).await()
            
            Log.d("AppRepository", "Estudiante $uid vinculado al curso $code")
            true
        } catch (e: Exception) {
            Log.e("AppRepository", "Error en registrarAsistencia: ${e.message}")
            false
        }
    }

    suspend fun getEstudiantesPorCurso(codigoCurso: String): List<Usuario> {
        return try {
            val code = formatCode(codigoCurso)
            val snapshot = db.collection("cursos").document(code)
                .collection("estudiantes").get().await()
            
            val estudiantesLinked = snapshot.documents.mapNotNull { doc ->
                doc.toObject(Usuario::class.java)?.copy(id = doc.id)
            }

            // Para cada estudiante, intentamos obtener el nombre real de la colección 'users'
            // Esto corrige datos antiguos o perfiles temporales
            estudiantesLinked.map { estu ->
                val perfilReal = getUsuario(estu.id)
                if (perfilReal != null) {
                    // Si el perfil en el curso estaba incompleto, lo actualizamos silenciosamente
                    if (estu.nombre.contains("Cargando") || estu.nombre.contains("Estudiante ") || estu.nombre.isEmpty()) {
                        db.collection("cursos").document(code)
                            .collection("estudiantes").document(estu.id)
                            .set(perfilReal)
                    }
                    perfilReal
                } else {
                    estu
                }
            }
        } catch (e: Exception) {
            Log.e("AppRepository", "Error en getEstudiantesPorCurso: ${e.message}")
            emptyList()
        }
    }

    suspend fun getAsistenciasPorCurso(codigoCurso: String): List<Asistencia> {
        return try {
            val snapshot = db.collection("asistencias")
                .whereEqualTo("codigoCurso", formatCode(codigoCurso))
                .get().await()
            snapshot.toObjects(Asistencia::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getCursosPorEstudiante(idEstudiante: String): List<Curso> {
        return try {
            val snapshot = db.collection("asistencias")
                .whereEqualTo("idEstudiante", idEstudiante.trim())
                .get().await()
            val codigos = snapshot.documents.mapNotNull { it.getString("codigoCurso") }.distinct()
            codigos.mapNotNull { getCursoPorCodigo(it) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getAsistenciasPorEstudianteYCurso(idEstudiante: String, codigoCurso: String): List<Asistencia> {
        return try {
            val snapshot = db.collection("asistencias")
                .whereEqualTo("idEstudiante", idEstudiante.trim())
                .whereEqualTo("codigoCurso", formatCode(codigoCurso))
                .get().await()
            snapshot.toObjects(Asistencia::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
