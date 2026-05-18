package com.example.proyectoapps.data

import android.util.Log
import com.example.proyectoapps.data.local.Asistencia
import com.example.proyectoapps.data.local.Curso
import com.example.proyectoapps.data.local.Usuario
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import java.time.LocalDate

class AppRepository {
    private val db = FirebaseFirestore.getInstance()

    private fun formatCode(code: String) = code.trim().uppercase()

    suspend fun getUsuario(uid: String): Usuario? {
        return try {
            val doc = db.collection("users").document(uid.trim()).get().await()
            if (!doc.exists()) return null
            doc.toObject(Usuario::class.java)?.copy(id = doc.id)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun crearCurso(curso: Curso): Boolean {
        return try {
            val normalizedCode = formatCode(curso.codigo)
            val profesor = getUsuario(curso.idProfesor)
            val cursoFinal = curso.copy(
                codigo = normalizedCode,
                nombreProfesor = profesor?.nombre ?: ""
            )
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
            
            val asistencia = Asistencia(
                idEstudiante = uid,
                codigoCurso = code,
                fecha = fechaActual,
                presente = true
            )
            val docId = "${uid}_${code}_$fechaActual"
            db.collection("asistencias").document(docId).set(asistencia).await()
            
            // Actualizar o inscribir con datos reales del usuario
            val user = getUsuario(uid)
            if (user != null) {
                db.collection("cursos").document(code)
                    .collection("estudiantes").document(uid)
                    .set(user).await()
            }
            
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getEstudiantesPorCurso(codigoCurso: String): List<Usuario> = coroutineScope {
        try {
            val code = formatCode(codigoCurso)
            val snapshot = db.collection("cursos").document(code)
                .collection("estudiantes").get().await()
            
            val listaBase = snapshot.documents.mapNotNull { doc ->
                val u = doc.toObject(Usuario::class.java)
                u?.copy(id = doc.id)
            }

            // Mapeo para asegurar nombres reales
            listaBase.map { estu ->
                async {
                    // Si el nombre es igual al ID o está vacío, buscamos el perfil real
                    if (estu.nombre.isBlank() || estu.nombre == estu.id || estu.nombre.contains("Cargando")) {
                        getUsuario(estu.id) ?: estu
                    } else {
                        estu
                    }
                }
            }.awaitAll()
        } catch (e: Exception) {
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
