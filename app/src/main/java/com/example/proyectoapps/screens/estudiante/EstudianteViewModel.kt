package com.example.proyectoapps.screens.estudiante

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectoapps.data.AppRepository
import com.example.proyectoapps.data.local.Asistencia
import com.example.proyectoapps.data.local.Curso
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EstudianteViewModel(private val repository: AppRepository) : ViewModel() {

    private val _cursos = MutableStateFlow<List<Curso>>(emptyList())
    val cursos: StateFlow<List<Curso>> = _cursos

    private val _asistencias = MutableStateFlow<List<Asistencia>>(emptyList())
    val asistencias: StateFlow<List<Asistencia>> = _asistencias

    fun cargarCursos(idEstudiante: String) {
        viewModelScope.launch {
            _cursos.value = repository.getCursosPorEstudiante(idEstudiante)
        }
    }

    fun cargarAsistencias(idEstudiante: String, codigoCurso: String) {
        viewModelScope.launch {
            _asistencias.value = repository.getAsistenciasPorEstudianteYCurso(idEstudiante, codigoCurso)
        }
    }

    suspend fun getCursoPorCodigo(codigo: String): Curso? {
        return repository.getCursoPorCodigo(codigo)
    }
}
