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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectoapps.CursoEstudiante
import com.example.proyectoapps.cursosEstudianteEjemplo
import com.example.proyectoapps.navegation.Routes
import com.example.proyectoapps.ui.theme.AppColors

// PANTALLA 1 — MIS CURSOS (ESTUDIANTE)
// ─────────────────────────────────────────────
@Composable
fun PantallaCursosEstudiante(
    navController: NavController,
    cursos: List<CursoEstudiante> = cursosEstudianteEjemplo,
    nombreEstudiante: String = "Alice Johnson"
) {
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppColors.AzulClaro)
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                Column(modifier = Modifier.align(Alignment.CenterStart)) {
                    Text(
                        text = "Mis Cursos",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = nombreEstudiante,
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                }
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = "Cerrar sesión",
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable { navController.navigate(Routes.LOGIN) }
                )
            }
        },
        // FAB con ícono QR en lugar del "+"
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Routes.MI_CODIGO_QR) },
                containerColor = AppColors.AzulClaro,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.QrCode,
                    contentDescription = "Mi código QR",
                    tint = Color.White
                )
            }
        },
        containerColor = AppColors.GrisFondo
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(cursos) { curso ->
                TarjetaCursoEstudiante(
                    curso = curso,
                    onClick = { navController.navigate(Routes.detalleEstudiante(curso.codigo)) }
                )
            }
        }
    }
}

// ─────────────────────────────────────────────
// COMPONENTE — TARJETA DE CURSO (ESTUDIANTE)
// ─────────────────────────────────────────────
@Composable
fun TarjetaCursoEstudiante(
    curso: CursoEstudiante,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.GrisTarjeta),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column {
            // Encabezado de color
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(curso.color)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Column {
                    Text(
                        text = curso.nombre,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = curso.codigo,
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                }
            }
            // Cuerpo
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                Text(
                    text = curso.descripcion,
                    fontSize = 13.sp,
                    color = AppColors.TextoSec
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(
                        text = "👥 ${curso.estudiantes} estudiantes",
                        fontSize = 12.sp,
                        color = AppColors.TextoSec
                    )
                    Text(
                        text = "📖 ${curso.sesiones} sesiones",
                        fontSize = 12.sp,
                        color = AppColors.TextoSec
                    )
                }
            }
        }
    }
}