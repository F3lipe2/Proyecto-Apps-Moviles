package com.example.proyectoapps.screens.profesor

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
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
import com.example.proyectoapps.data.local.Curso
import com.example.proyectoapps.navegation.Routes
import com.example.proyectoapps.ui.theme.AppColors
import com.example.proyectoapps.utils.SharedPrefsHelper

@Composable
fun PantallaMisCursos(
    navController: NavController,
    viewModel: ProfesorViewModel = viewModel(factory = ProfesorViewModelFactory(AppRepository()))
) {
    val context = LocalContext.current
    val prefs = remember { SharedPrefsHelper(context) }
    val idProfesor = prefs.getUserId()
    val nombreProfesor = prefs.getNombre()
    
    val cursos by viewModel.cursos.collectAsState()

    LaunchedEffect(idProfesor) {
        if (idProfesor.isNotEmpty()) {
            viewModel.cargarCursos(idProfesor)
        }
    }

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
                        text = nombreProfesor,
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
                        .clickable { 
                            com.google.firebase.auth.FirebaseAuth.getInstance().signOut()
                            prefs.cerrarSesion()
                            navController.navigate(Routes.LOGIN) {
                                popUpTo(0)
                            }
                        }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Routes.NUEVO_CURSO) },
                containerColor = AppColors.AzulClaro,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Añadir curso",
                    tint = Color.White
                )
            }
        },
        containerColor = AppColors.GrisFondo
    ) { paddingValues ->
        if (cursos.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                Text(text = "No tienes cursos creados", color = AppColors.TextoSec)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(cursos) { curso ->
                    TarjetaCurso(
                        curso = curso,
                        onClick = { navController.navigate(Routes.detalleProfesor(curso.codigo)) }
                    )
                }
            }
        }
    }
}

@Composable
fun TarjetaCurso(curso: Curso, onClick: () -> Unit = {}, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.GrisTarjeta),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column {
            val cardColor = remember(curso.color) {
                try { Color(android.graphics.Color.parseColor(curso.color)) }
                catch (e: Exception) { AppColors.AzulClaro }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(cardColor)
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

            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                Text(
                    text = curso.descripcion,
                    fontSize = 13.sp,
                    color = AppColors.TextoSec
                )
            }
        }
    }
}
