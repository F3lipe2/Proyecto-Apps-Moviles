package com.example.proyectoapps.screens.estudiante

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectoapps.data.AppRepository
import com.example.proyectoapps.data.local.Asistencia
import com.example.proyectoapps.navegation.Routes
import com.example.proyectoapps.ui.theme.AppColors
import com.example.proyectoapps.utils.SharedPrefsHelper
import com.google.firebase.auth.FirebaseAuth

@Composable
fun PantallaHistorialEstudiante(
    codigoCurso: String,
    navController: NavController,
    viewModel: EstudianteViewModel = viewModel(factory = EstudianteViewModelFactory(AppRepository()))
) {
    val context = LocalContext.current
    val prefs = remember { SharedPrefsHelper(context) }
    val idEstudiante = prefs.getUserId()
    
    val asistencias by viewModel.asistencias.collectAsState()
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(codigoCurso) {
        val curso = viewModel.getCursoPorCodigo(codigoCurso)
        curso?.let {
            viewModel.cargarAsistencias(idEstudiante, it.codigo)
        }
        isLoading = false
    }

    val totalClases  = asistencias.size.coerceAtLeast(1) // O un valor de sesiones totales del curso
    val presentes    = asistencias.count { it.presente }
    val ausentes     = asistencias.count { !it.presente }
    val porcentaje   = (presentes * 100) / totalClases

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
                    .clickable {
                        FirebaseAuth.getInstance().signOut()
                        prefs.cerrarSesion()
                        navController.navigate(Routes.LOGIN) {
                            popUpTo(0)
                        }
                    }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = AppColors.AzulClaro)
            }
        } else {
            // Tarjeta resumen
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
                        Estadistica(valor = "$porcentaje%", etiqueta = "Asistencia", color = AppColors.TextoPrin)
                        Estadistica(valor = "$presentes", etiqueta = "Presentes", color = Color(0xFF388E3C))
                        Estadistica(valor = "$ausentes", etiqueta = "Ausentes", color = Color(0xFFD32F2F))
                        Estadistica(valor = "$totalClases", etiqueta = "Clases", color = AppColors.TextoPrin)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (asistencias.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Aún no tienes registros de asistencia", color = AppColors.TextoSec)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(asistencias) { registro ->
                        TarjetaRegistroAsistenciaLocal(registro = registro)
                    }
                }
            }
        }
    }
}

@Composable
fun Estadistica(valor: String, etiqueta: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = valor, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = color)
        Text(text = etiqueta, fontSize = 11.sp, color = AppColors.TextoSec)
    }
}

@Composable
fun TarjetaRegistroAsistenciaLocal(registro: Asistencia) {
    val colorEstado  = if (registro.presente) Color(0xFF388E3C) else Color(0xFFD32F2F)
    val fondoIcono   = if (registro.presente) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
    val iconoEstado  = if (registro.presente) Icons.Default.CheckCircle else Icons.Default.Cancel
    val textoEstado  = if (registro.presente) "Presente" else "Ausente"

    Card(
        modifier = Modifier.fillMaxWidth(),
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
                modifier = Modifier.size(40.dp).background(fondoIcono, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = iconoEstado, contentDescription = null, tint = colorEstado, modifier = Modifier.size(22.dp))
            }
            Spacer(modifier = Modifier.width(14.dp))
            Text(text = registro.fecha, fontSize = 14.sp, color = AppColors.TextoPrin, modifier = Modifier.weight(1f))
            Text(text = textoEstado, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = colorEstado)
        }
    }
}
