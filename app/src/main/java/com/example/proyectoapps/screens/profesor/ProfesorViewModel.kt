package com.example.proyectoapps.screens.profesor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectoapps.data.AppRepository
import com.example.proyectoapps.data.local.Asistencia
import com.example.proyectoapps.data.local.Curso
import com.example.proyectoapps.data.local.Usuario
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ProfesorViewModel(private val repository: AppRepository) : ViewModel() {

    private val _cursos = MutableStateFlow<List<Curso>>(emptyList())
    val cursos: StateFlow<List<Curso>> = _cursos

    private val _estudiantes = MutableStateFlow<List<Usuario>>(emptyList())
    val estudiantes: StateFlow<List<Usuario>> = _estudiantes

    private val _asistencias = MutableStateFlow<List<Asistencia>>(emptyList())
    val asistencias: StateFlow<List<Asistencia>> = _asistencias

    private val _registroExitoso = MutableSharedFlow<Boolean>()
    val registroExitoso: SharedFlow<Boolean> = _registroExitoso.asSharedFlow()

    fun cargarCursos(idProfesor: String) {
        viewModelScope.launch {
            _cursos.value = repository.getCursosPorProfesor(idProfesor)
        }
    }

    fun crearCurso(nombre: String, codigo: String, descripcion: String, idProfesor: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val nuevoCurso = Curso(
                nombre = nombre,
                codigo = codigo,
                descripcion = descripcion,
                idProfesor = idProfesor
            )
            val exito = repository.crearCurso(nuevoCurso)
            if (exito) cargarCursos(idProfesor)
            onResult(exito)
        }
    }

    fun cargarEstudiantes(codigoCurso: String) {
        viewModelScope.launch {
            _estudiantes.value = repository.getEstudiantesPorCurso(codigoCurso)
        }
    }

    fun registrarAsistencia(idEstudiante: String, codigoCurso: String) {
        viewModelScope.launch {
            val exito = repository.registrarAsistencia(idEstudiante, codigoCurso)
            if (exito) {
                cargarAsistencias(codigoCurso)
                cargarEstudiantes(codigoCurso)
            }
            _registroExitoso.emit(exito)
        }
    }

    fun cargarAsistencias(codigoCurso: String) {
        viewModelScope.launch {
            _asistencias.value = repository.getAsistenciasPorCurso(codigoCurso)
        }
    }

    suspend fun getCursoPorCodigo(codigo: String): Curso? {
        return repository.getCursoPorCodigo(codigo)
    }
}
