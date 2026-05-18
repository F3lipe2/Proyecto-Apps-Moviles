package com.example.proyectoapps.screens.profesor

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectoapps.data.AppRepository
import com.example.proyectoapps.data.local.Asistencia
import com.example.proyectoapps.data.local.Usuario
import com.example.proyectoapps.ui.theme.AppColors

@Composable
fun PantallaHistorialAsistencia(
    codigoCurso: String,
    navController: NavController,
    viewModel: ProfesorViewModel = viewModel(factory = ProfesorViewModelFactory(AppRepository()))
) {
    val asistencias by viewModel.asistencias.collectAsState()
    val estudiantes by viewModel.estudiantes.collectAsState()
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(codigoCurso) {
        viewModel.cargarEstudiantes(codigoCurso)
        viewModel.cargarAsistencias(codigoCurso)
        isLoading = false
    }

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

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = AppColors.AzulClaro)
            }
        } else {
            if (estudiantes.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "No hay estudiantes en este curso", color = AppColors.TextoSec)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(estudiantes) { estudiante ->
                        // Filtramos las asistencias de este estudiante específico
                        val asistenciasEstudiante = asistencias.filter { 
                            it.idEstudiante.trim() == estudiante.id.trim() 
                        }
                        
                        val totalClases = asistencias.map { it.fecha }.distinct().size.coerceAtLeast(1)
                        
                        TarjetaAsistenciaEstudianteLocal(
                            estudiante = estudiante,
                            registros = asistenciasEstudiante,
                            totalClases = totalClases
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TarjetaAsistenciaEstudianteLocal(
    estudiante: Usuario,
    registros: List<Asistencia>,
    totalClases: Int
) {
    var expandida by remember { mutableStateOf(false) }
    val presentes = registros.count { it.presente }
    val porcentaje = (presentes * 100) / totalClases

    val colorPorcentaje = if (porcentaje >= 75) Color(0xFF388E3C) else Color(0xFFD32F2F)
    val fondoPorcentaje = if (porcentaje >= 75) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expandida = !expandida },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.GrisTarjeta),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFFE3F2FD), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = estudiante.nombre.ifEmpty { "E" }.first().toString().uppercase(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.AzulClaro
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = estudiante.nombre.ifEmpty { "Estudiante Sin Nombre" },
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AppColors.TextoPrin
                    )
                    Text(
                        text = "$presentes / $totalClases asistencias",
                        fontSize = 12.sp,
                        color = colorPorcentaje
                    )
                }

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
                    imageVector = if (expandida) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = AppColors.TextoSec,
                    modifier = Modifier.size(20.dp)
                )
            }

            AnimatedVisibility(visible = expandida) {
                Column {
                    Spacer(modifier = Modifier.height(10.dp))
                    HorizontalDivider(color = Color(0xFFEEEEEE))
                    Spacer(modifier = Modifier.height(8.dp))
                    if (registros.isEmpty()) {
                        Text(text = "Sin registros", fontSize = 12.sp, color = AppColors.TextoSec)
                    } else {
                        registros.forEach { registro ->
                            FilaRegistroAsistenciaLocal(registro = registro)
                            Spacer(modifier = Modifier.height(6.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FilaRegistroAsistenciaLocal(registro: Asistencia) {
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
                imageVector = if (registro.presente) Icons.Default.CheckCircle else Icons.Default.Cancel,
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
