package com.example.proyectoapps.screens.profesor

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectoapps.AsistenciaEstudiante
import com.example.proyectoapps.RegistroAsistencia
import com.example.proyectoapps.TopBarAzul
import com.example.proyectoapps.asistenciasEjemplo
import com.example.proyectoapps.ui.theme.AppColors

// PANTALLA 5 — HISTORIAL DE ASISTENCIA
// ─────────────────────────────────────────────
@Composable
fun PantallaHistorialAsistencia(
    codigoCurso: String = "CS 101",
    asistencias: List<AsistenciaEstudiante> = asistenciasEjemplo,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.GrisFondo)
    ) {
        TopBarAzul(
            titulo = "Historial de Asistencia",
            subtitulo = codigoCurso,
            onVolver = { navController.popBackStack() }
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(asistencias) { asistencia ->
                TarjetaAsistenciaEstudiante(asistencia = asistencia)
            }
        }
    }
}

// ─────────────────────────────────────────────
// COMPONENTE — TARJETA ASISTENCIA CON EXPANSIÓN
// ─────────────────────────────────────────────
@Composable
fun TarjetaAsistenciaEstudiante(
    asistencia: AsistenciaEstudiante,
    modifier: Modifier = Modifier
) {
    val presentes = asistencia.registros.count { it.presente }
    val porcentaje = if (asistencia.totalClases > 0)
        (presentes * 100) / asistencia.totalClases else 0
    val expandida = asistencia.registros.isNotEmpty()

    val colorPorcentaje = when {
        porcentaje >= 80 -> Color(0xFF388E3C)
        porcentaje >= 50 -> Color(0xFFF57C00)
        else             -> Color(0xFFD32F2F)
    }
    val fondoPorcentaje = when {
        porcentaje >= 80 -> Color(0xFFE8F5E9)
        porcentaje >= 50 -> Color(0xFFFFF3E0)
        else             -> Color(0xFFFFEBEE)
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.GrisTarjeta),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Fila principal
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar con inicial
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFFE3F2FD), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = asistencia.estudiante.nombre.first().toString(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.AzulClaro
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = asistencia.estudiante.nombre,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AppColors.TextoPrin
                    )
                    Text(
                        text = "$presentes / ${asistencia.totalClases} clases",
                        fontSize = 12.sp,
                        color = colorPorcentaje
                    )
                }

                // Badge de porcentaje
                Box(
                    modifier = Modifier
                        .background(fondoPorcentaje, RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "$porcentaje%",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorPorcentaje
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    imageVector = if (expandida) Icons.Default.KeyboardArrowUp
                    else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = AppColors.TextoSec,
                    modifier = Modifier.size(20.dp)
                )
            }

            // Registros expandidos
            if (expandida) {
                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(color = Color(0xFFEEEEEE))
                Spacer(modifier = Modifier.height(8.dp))
                asistencia.registros.forEach { registro ->
                    FilaRegistroAsistencia(registro = registro)
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        }
    }
}

// ─────────────────────────────────────────────
// COMPONENTE — FILA DE REGISTRO (fecha + estado)
// ─────────────────────────────────────────────
@Composable
fun FilaRegistroAsistencia(registro: RegistroAsistencia) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = registro.fecha,
            fontSize = 13.sp,
            color = AppColors.TextoSec
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = if (registro.presente) Icons.Default.CheckCircle
                else Icons.Default.Cancel,
                contentDescription = null,
                tint = if (registro.presente) Color(0xFF388E3C) else Color(0xFFD32F2F),
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = if (registro.presente) "Presente" else "Ausente",
                fontSize = 13.sp,
                color = if (registro.presente) Color(0xFF388E3C) else Color(0xFFD32F2F)
            )
        }
    }
}