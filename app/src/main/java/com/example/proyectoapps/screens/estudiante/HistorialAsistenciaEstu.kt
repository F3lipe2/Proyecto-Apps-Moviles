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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectoapps.RegistroClase
import com.example.proyectoapps.navegation.Routes
import com.example.proyectoapps.registrosEjemplo
import com.example.proyectoapps.ui.theme.AppColors

// PANTALLA 4 — HISTORIAL DE ASISTENCIA (ESTUDIANTE)
// ─────────────────────────────────────────────
@Composable
fun PantallaHistorialEstudiante(
    codigoCurso: String = "CS 101",
    registros: List<RegistroClase> = registrosEjemplo,
    navController: NavController
) {
    val totalClases  = registros.size
    val presentes    = registros.count { it.presente }
    val ausentes     = registros.count { !it.presente }
    val porcentaje   = if (totalClases > 0) (presentes * 100) / totalClases else 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.GrisFondo)
    ) {
        // TopBar
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
                    .clickable { navController.popBackStack() }
            )
            Column(modifier = Modifier.align(Alignment.Center)) {
                Text(
                    text = "Historial de Asistencia",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = codigoCurso,
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.85f)
                )
            }
            Icon(
                imageVector = Icons.Default.ExitToApp,
                contentDescription = "Salir",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable { navController.navigate(Routes.LOGIN) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tarjeta resumen de asistencia
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = AppColors.GrisTarjeta),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Mi Asistencia",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.TextoPrin
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    EstadisticaAsistencia(
                        valor = "$porcentaje%",
                        etiqueta = "Asistencia",
                        color = AppColors.TextoPrin
                    )
                    EstadisticaAsistencia(
                        valor = "$presentes",
                        etiqueta = "Presentes",
                        color = Color(0xFF388E3C)
                    )
                    EstadisticaAsistencia(
                        valor = "$ausentes",
                        etiqueta = "Ausentes",
                        color = Color(0xFFD32F2F)
                    )
                    EstadisticaAsistencia(
                        valor = "$totalClases",
                        etiqueta = "Clases",
                        color = AppColors.TextoPrin
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de registros individuales
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(registros) { registro ->
                TarjetaRegistroClase(registro = registro)
            }
        }
    }
}

// ─────────────────────────────────────────────
// COMPONENTE — ESTADÍSTICA INDIVIDUAL
// (porcentaje, presentes, ausentes, clases)
// ─────────────────────────────────────────────
@Composable
fun EstadisticaAsistencia(
    valor: String,
    etiqueta: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = valor,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = etiqueta,
            fontSize = 11.sp,
            color = AppColors.TextoSec
        )
    }
}

// ─────────────────────────────────────────────
// COMPONENTE — TARJETA DE REGISTRO DE CLASE
// ─────────────────────────────────────────────
@Composable
fun TarjetaRegistroClase(
    registro: RegistroClase,
    modifier: Modifier = Modifier
) {
    val colorEstado  = if (registro.presente) Color(0xFF388E3C) else Color(0xFFD32F2F)
    val fondoIcono   = if (registro.presente) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
    val iconoEstado  = if (registro.presente) Icons.Default.CheckCircle else Icons.Default.Cancel
    val textoEstado  = if (registro.presente) "Presente" else "Ausente"

    Card(
        modifier = modifier.fillMaxWidth(),
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
            // Ícono de estado
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(fondoIcono, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = iconoEstado,
                    contentDescription = textoEstado,
                    tint = colorEstado,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Fecha
            Text(
                text = registro.fecha,
                fontSize = 14.sp,
                color = AppColors.TextoPrin,
                modifier = Modifier.weight(1f)
            )

            // Estado
            Text(
                text = textoEstado,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorEstado
            )
        }
    }
}