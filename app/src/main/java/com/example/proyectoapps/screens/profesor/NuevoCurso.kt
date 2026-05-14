package com.example.proyectoapps.screens.profesor

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Book
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
fun PantallaNuevoCurso(
    navController: NavController,
    viewModel: ProfesorViewModel = viewModel(factory = ProfesorViewModelFactory(AppRepository()))
) {
    val context = LocalContext.current
    val prefs = remember { SharedPrefsHelper(context) }
    val idProfesor = prefs.getUserId()

    var nombre by remember { mutableStateOf("") }
    var codigo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var errorNombre by remember { mutableStateOf(false) }
    var errorCodigo by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.GrisFondo)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppColors.AzulClaro)
                .statusBarsPadding()
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.White,
                        modifier = Modifier.clickable { navController.popBackStack() }
                    )
                    Text(
                        text = "Nuevo Curso",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Salir",
                        tint = Color.White,
                        modifier = Modifier.clickable { 
                            com.google.firebase.auth.FirebaseAuth.getInstance().signOut()
                            prefs.cerrarSesion()
                            navController.navigate(Routes.LOGIN) {
                                popUpTo(0)
                            }
                        }
                    )
                }

                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .background(Color.White.copy(alpha = 0.2f), RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Book,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = if(nombre.isEmpty()) "Nombre del curso" else nombre,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                        Text(
                            text = if(codigo.isEmpty()) "CÓDIGO" else codigo.uppercase(),
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            CampoFormSimple(
                value = nombre,
                onValueChange = { nombre = it; errorNombre = false },
                etiqueta = "Nombre del curso",
                placeholder = "Ej. Introducción a la Programación",
                isError = errorNombre
            )

            CampoFormSimple(
                value = codigo,
                onValueChange = { codigo = it; errorCodigo = false },
                etiqueta = "Código del curso",
                placeholder = "EJ. CS 101",
                isError = errorCodigo
            )

            Column {
                Row {
                    Text(text = "Descripción", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = AppColors.TextoPrin)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "(opcional)", fontSize = 13.sp, color = AppColors.TextoSec)
                }
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { if (it.length <= 120) descripcion = it },
                    placeholder = { Text("Describe brevemente el contenido del curso...", color = AppColors.TextoSec, fontSize = 13.sp) },
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color(0xFFE0E0E0),
                        focusedBorderColor = AppColors.AzulClaro
                    )
                )
                Text(
                    text = "${descripcion.length}/120",
                    fontSize = 11.sp,
                    color = AppColors.TextoSec,
                    modifier = Modifier.align(Alignment.End).padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (nombre.isBlank()) errorNombre = true
                    if (codigo.isBlank()) errorCodigo = true
                    
                    if (nombre.isNotBlank() && codigo.isNotBlank()) {
                        viewModel.crearCurso(nombre, codigo, descripcion, idProfesor) { success ->
                            if (success) {
                                Toast.makeText(context, "Curso creado con éxito", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            } else {
                                Toast.makeText(context, "Error al crear el curso", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.AzulClaro)
            ) {
                Text(text = "Crear Curso", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
            }
        }
    }
}

@Composable
fun CampoFormSimple(
    value: String,
    onValueChange: (String) -> Unit,
    etiqueta: String,
    placeholder: String,
    isError: Boolean
) {
    Column {
        Row {
            Text(text = etiqueta, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = AppColors.TextoPrin)
            Spacer(modifier = Modifier.width(2.dp))
            Text(text = "*", fontSize = 13.sp, color = Color(0xFFD32F2F))
        }
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = AppColors.TextoSec, fontSize = 13.sp) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            isError = isError,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFE0E0E0),
                focusedBorderColor = AppColors.AzulClaro
            )
        )
        if (isError) {
            Text(text = "Este campo es obligatorio", color = Color.Red, fontSize = 12.sp)
        }
    }
}
