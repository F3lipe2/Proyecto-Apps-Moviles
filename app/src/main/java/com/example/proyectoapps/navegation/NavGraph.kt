package com.example.proyectoapps.navegation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.proyectoapps.screens.auth.PantallaLogin
import com.example.proyectoapps.screens.auth.PantallaRegister
import com.example.proyectoapps.utils.SharedPrefsHelper
import com.example.proyectoapps.screens.estudiante.PantallaMiCodigoQR
import com.example.proyectoapps.screens.estudiante.PantallaCursosEstudiante
import com.example.proyectoapps.screens.estudiante.PantallaDetalleCursoEstudiante
import com.example.proyectoapps.screens.estudiante.PantallaHistorialEstudiante
import com.example.proyectoapps.screens.profesor.PantallaDetalleCurso
import com.example.proyectoapps.screens.profesor.PantallaEscanerQR
import com.example.proyectoapps.screens.profesor.PantallaEstudiantes
import com.example.proyectoapps.screens.profesor.PantallaHistorialAsistencia
import com.example.proyectoapps.screens.profesor.PantallaMisCursos
import com.example.proyectoapps.screens.profesor.PantallaNuevoCurso

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val prefs   = SharedPrefsHelper(context)

    // Determinar pantalla inicial según si hay sesión guardada
    val startDest = when {
        !prefs.estaLogueado()          -> Routes.LOGIN
        prefs.getRol() == "Profesor"   -> Routes.CURSOS_PROFESOR
        else                           -> Routes.CURSOS_ESTUDIANTE
    }

    NavHost(
        navController = navController,
        startDestination = startDest
    ) {

        // ── AUTH ─────────────────────────────────────────────
        composable(Routes.LOGIN) {
            PantallaLogin(navController = navController)
        }

        composable(Routes.REGISTER) {
            PantallaRegister(navController = navController)
        }

        // ── PROFESOR ─────────────────────────────────────────
        composable(Routes.CURSOS_PROFESOR) {
            PantallaMisCursos(navController = navController)
        }

        composable(
            route = Routes.DETALLE_PROFESOR,
            arguments = listOf(navArgument("codigoCurso") { type = NavType.StringType })
        ) { backStackEntry ->
            val codigoCurso = backStackEntry.arguments?.getString("codigoCurso") ?: ""
            PantallaDetalleCurso(codigoCurso = codigoCurso, navController = navController)
        }

        composable(
            route = Routes.ESTUDIANTES,
            arguments = listOf(navArgument("codigoCurso") { type = NavType.StringType })
        ) { backStackEntry ->
            val codigoCurso = backStackEntry.arguments?.getString("codigoCurso") ?: ""
            PantallaEstudiantes(codigoCurso = codigoCurso, navController = navController)
        }

        composable(
            route = Routes.HISTORIAL_PROFESOR,
            arguments = listOf(navArgument("codigoCurso") { type = NavType.StringType })
        ) { backStackEntry ->
            val codigoCurso = backStackEntry.arguments?.getString("codigoCurso") ?: ""
            PantallaHistorialAsistencia(codigoCurso = codigoCurso, navController = navController)
        }

        composable(Routes.NUEVO_CURSO) {
            PantallaNuevoCurso(navController = navController)
        }

        composable(
            route = Routes.ESCANER_QR,
            arguments = listOf(navArgument("codigoCurso") { type = NavType.StringType })
        ) { backStackEntry ->
            val codigoCurso = backStackEntry.arguments?.getString("codigoCurso") ?: ""
            PantallaEscanerQR(codigoCurso = codigoCurso, navController = navController)
        }

        // ── ESTUDIANTE ────────────────────────────────────────
        composable(Routes.CURSOS_ESTUDIANTE) {
            PantallaCursosEstudiante(navController = navController)
        }

        composable(
            route = Routes.DETALLE_ESTUDIANTE,
            arguments = listOf(navArgument("codigoCurso") { type = NavType.StringType })
        ) { backStackEntry ->
            val codigoCurso = backStackEntry.arguments?.getString("codigoCurso") ?: ""
            PantallaDetalleCursoEstudiante(codigoCurso = codigoCurso, navController = navController)
        }

        composable(
            route = Routes.HISTORIAL_ESTUDIANTE,
            arguments = listOf(navArgument("codigoCurso") { type = NavType.StringType })
        ) { backStackEntry ->
            val codigoCurso = backStackEntry.arguments?.getString("codigoCurso") ?: ""
            PantallaHistorialEstudiante(codigoCurso = codigoCurso, navController = navController)
        }

        composable(Routes.MI_CODIGO_QR) {
            PantallaMiCodigoQR(navController = navController)
        }
    }
}
