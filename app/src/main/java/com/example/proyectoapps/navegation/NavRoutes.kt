package com.example.proyectoapps.navegation

object Routes {
    // Auth
    const val LOGIN    = "login"
    const val REGISTER = "register"

    // Profesor
    const val CURSOS_PROFESOR    = "cursos_profesor"
    const val DETALLE_PROFESOR   = "detalle_profesor/{codigoCurso}"
    const val ESTUDIANTES        = "estudiantes/{codigoCurso}"
    const val HISTORIAL_PROFESOR = "historial_profesor/{codigoCurso}"
    const val NUEVO_CURSO        = "nuevo_curso"
    const val ESCANER_QR         = "escaner_qr/{codigoCurso}"

    // Estudiante
    const val CURSOS_ESTUDIANTE    = "cursos_estudiante"
    const val DETALLE_ESTUDIANTE   = "detalle_estudiante/{codigoCurso}"
    const val HISTORIAL_ESTUDIANTE = "historial_estudiante/{codigoCurso}"
    const val MI_CODIGO_QR         = "mi_codigo_qr"

    // Funciones para construir rutas con argumentos
    fun detalleProfesor(codigo: String)   = "detalle_profesor/$codigo"
    fun detalleEstudiante(codigo: String) = "detalle_estudiante/$codigo"
    fun estudiantes(codigo: String)       = "estudiantes/$codigo"
    fun historialProfesor(codigo: String) = "historial_profesor/$codigo"
    fun historialEstudiante(codigo: String) = "historial_estudiante/$codigo"
    fun escanerQR(codigo: String)         = "escaner_qr/$codigo"
}