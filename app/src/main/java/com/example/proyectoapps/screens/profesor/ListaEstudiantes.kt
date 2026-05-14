package com.example.proyectoapps.screens.profesor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectoapps.data.AppRepository
import com.example.proyectoapps.data.local.Usuario
import com.example.proyectoapps.ui.theme.AppColors

@Composable
fun PantallaEstudiantes(
    codigoCurso: String,
    navController: NavController,
    viewModel: ProfesorViewModel = viewModel(factory = ProfesorViewModelFactory(AppRepository()))
) {
    val estudiantes by viewModel.estudiantes.collectAsState()
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(codigoCurso) {
        val curso = viewModel.getCursoPorCodigo(codigoCurso)
        curso?.let {
            viewModel.cargarEstudiantes(it.codigo)
        }
        isLoading = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.GrisFondo)
    ) {
        TopBarAzul(
            titulo = "Estudiantes",
            subtitulo = codigoCurso,
            onVolver = { navController.popBackStack() }
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = AppColors.AzulClaro)
            }
        } else {
            Text(
                text = "${estudiantes.size} ESTUDIANTES INSCRITOS",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = AppColors.TextoSec,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (estudiantes.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "No hay estudiantes inscritos", color = AppColors.TextoSec)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(estudiantes) { estudiante ->
                        TarjetaEstudianteLocal(estudiante = estudiante)
                    }
                }
            }
        }
    }
}

@Composable
fun TarjetaEstudianteLocal(estudiante: Usuario, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.GrisTarjeta),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(Color(0xFFE3F2FD), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = AppColors.AzulClaro,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                val displayNombre = if (estudiante.nombre.startsWith("Estudiante ")) {
                    "ID: ${estudiante.id.take(8)}..."
                } else {
                    estudiante.nombre
                }
                Text(
                    text = displayNombre,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.TextoPrin
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (estudiante.nombre.startsWith("Estudiante ")) Icons.Default.Person else Icons.Default.Email,
                        contentDescription = null,
                        tint = AppColors.TextoSec,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (estudiante.nombre.startsWith("Estudiante ")) "Perfil no sincronizado" else estudiante.correo,
                        fontSize = 11.sp,
                        color = AppColors.TextoSec
                    )
                }
            }
            
            Text(
                text = "ID: ${estudiante.id}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = AppColors.TextoSec
            )
        }
    }
}
