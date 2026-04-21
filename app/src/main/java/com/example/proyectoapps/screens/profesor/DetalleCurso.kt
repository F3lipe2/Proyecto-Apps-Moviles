package com.example.proyectoapps.screens.profesor

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectoapps.Curso
import com.example.proyectoapps.cursosEjemplo
import com.example.proyectoapps.navegation.Routes
import com.example.proyectoapps.ui.theme.AppColors

// PANTALLA 3 — DETALLE DE CURSO
// ─────────────────────────────────────────────
@Composable
fun PantallaDetalleCurso(
    curso: Curso = cursosEjemplo.first(),
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.GrisFondo)
    ) {
        // TopBar con fondo azul que incluye el encabezado del curso
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

                // Título grande del curso
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

        // Opciones del curso
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ItemOpcionCurso(
                titulo = "Tomar Asistencia",
                subtitulo = "Escanear códigos QR de estudiantes",
                colorIconoFondo = Color(0xFFE3F2FD),
                colorIcono = AppColors.IconQR,
                icono = Icons.Default.Add,
                onClick = { navController.navigate(Routes.escanerQR(curso.codigo)) }
            )
            ItemOpcionCurso(
                titulo = "Ver Estudiantes",
                subtitulo = "${curso.estudiantes} estudiantes inscritos",
                colorIconoFondo = Color(0xFFE8F5E9),
                colorIcono = AppColors.IconPerson,
                icono = Icons.Default.Add,
                onClick = { navController.navigate(Routes.estudiantes(curso.codigo)) }
            )
            ItemOpcionCurso(
                titulo = "Historial de Asistencia",
                subtitulo = "Ver todos los registros",
                colorIconoFondo = Color(0xFFFFF3E0),
                colorIcono = AppColors.IconHistory,
                icono = Icons.Default.Add,
                onClick = { navController.navigate(Routes.historialProfesor(curso.codigo)) }
            )
        }
    }
}

// ─────────────────────────────────────────────
// COMPONENTE — ÍTEM DE OPCIÓN EN DETALLE CURSO
// ─────────────────────────────────────────────
@Composable
fun ItemOpcionCurso(
    titulo: String,
    subtitulo: String,
    colorIconoFondo: Color,
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
            // Ícono con fondo de color
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .background(colorIconoFondo, RoundedCornerShape(10.dp)),
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

            // Textos
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


// ─────────────────────────────────────────────
// COMPONENTE REUTILIZABLE — TOP BAR AZUL
// ─────────────────────────────────────────────
@Composable
fun TopBarAzul(
    titulo: String,
    subtitulo: String,
    onVolver: () -> Unit = {},
    onSalir: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.AzulClaro)
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Volver",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickable { onVolver() }
        )
        Column(modifier = Modifier.align(Alignment.Center)) {
            Text(
                text = titulo,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Text(
                text = subtitulo,
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.85f),
                textAlign = TextAlign.Center
            )
        }
        Icon(
            imageVector = Icons.Default.ExitToApp,
            contentDescription = "Salir",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clickable { onSalir() }
        )
    }
}