package com.example.proyectoapps.screens.estudiante

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectoapps.CursoEstudiante
import com.example.proyectoapps.cursosEstudianteEjemplo
import com.example.proyectoapps.navegation.Routes
import com.example.proyectoapps.ui.theme.AppColors

// PANTALLA 2 — DETALLE DEL CURSO (ESTUDIANTE)
// ─────────────────────────────────────────────
@Composable
fun PantallaDetalleCursoEstudiante(
    curso: CursoEstudiante = cursosEstudianteEjemplo.first(),
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.GrisFondo)
    ) {
        // TopBar expandido azul
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppColors.AzulClaro)
                .statusBarsPadding()
        ) {
            Column {
                // Fila de navegación
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.White,
                        modifier = Modifier.clickable { navController.popBackStack() }
                    )
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = curso.codigo,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = curso.nombre,
                            fontSize = 11.sp,
                            color = Color.White.copy(alpha = 0.85f)
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Salir",
                        tint = Color.White,
                        modifier = Modifier.clickable { navController.navigate(Routes.LOGIN) }
                    )
                }
                // Título grande
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
                    Text(
                        text = curso.nombre,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = curso.descripcion,
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Solo 2 opciones para el estudiante
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ItemOpcionEstudiante(
                titulo = "Ver Estudiantes",
                subtitulo = "${curso.estudiantes} estudiantes inscritos",
                colorFondoIcono = Color(0xFFE8F5E9),
                colorIcono = AppColors.IconPerson,
                icono = Icons.Default.Group,
                onClick = { /* sin ruta propia, pantalla de sólo lectura */ }
            )
            ItemOpcionEstudiante(
                titulo = "Historial de Asistencia",
                subtitulo = "Ver todos los registros",
                colorFondoIcono = Color(0xFFFFF3E0),
                colorIcono = AppColors.IconHistory,
                icono = Icons.Default.Assignment,
                onClick = { navController.navigate(Routes.historialEstudiante(curso.codigo)) }
            )
        }
    }
}

// ─────────────────────────────────────────────
// COMPONENTE — ÍTEM DE OPCIÓN (ESTUDIANTE)
// ─────────────────────────────────────────────
@Composable
fun ItemOpcionEstudiante(
    titulo: String,
    subtitulo: String,
    colorFondoIcono: Color,
    colorIcono: Color,
    icono: ImageVector,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.GrisTarjeta),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .background(colorFondoIcono, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icono,
                    contentDescription = titulo,
                    tint = colorIcono,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column {
                Text(
                    text = titulo,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.TextoPrin
                )
                Text(
                    text = subtitulo,
                    fontSize = 12.sp,
                    color = AppColors.TextoSec
                )
            }
        }
    }
}