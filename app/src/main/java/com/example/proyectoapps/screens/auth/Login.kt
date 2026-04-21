package com.example.proyectoapps.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectoapps.data.local.AppDatabase
import com.example.proyectoapps.navegation.Routes
import com.example.proyectoapps.ui.theme.AppColors
import com.example.proyectoapps.utils.SharedPrefsHelper
import kotlinx.coroutines.launch

// PANTALLA 1 — LOGIN
// ─────────────────────────────────────────────
@Composable
fun PantallaLogin(navController: NavController) {

    val context     = LocalContext.current
    val dao         = remember { AppDatabase.getInstance(context).usuarioDao() }
    val prefs       = remember { SharedPrefsHelper(context) }
    val scope       = rememberCoroutineScope()

    var correo      by remember { mutableStateOf("") }
    var contrasena  by remember { mutableStateOf("") }
    var error       by remember { mutableStateOf("") }
    var cargando    by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.GrisFondo)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Ícono superior
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(AppColors.AzulClaro, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Logo",
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Título y subtítulo
        Text(
            text = "Control de Asistencia",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.TextoPrin
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Inicia sesión para continuar",
            fontSize = 14.sp,
            color = AppColors.TextoSec
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Tarjeta del formulario
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = AppColors.GrisTarjeta),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {

                // Campo correo
                Text(
                    text = "Correo electrónico",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = AppColors.TextoPrin
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = correo,
                    onValueChange = { correo = it; error = "" },
                    placeholder = { Text("tu.correo@universidad.edu", color = AppColors.TextoSec, fontSize = 13.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color(0xFFE0E0E0),
                        focusedBorderColor = AppColors.AzulClaro
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo contraseña
                Text(
                    text = "Contraseña",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = AppColors.TextoPrin
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = contrasena,
                    onValueChange = { contrasena = it; error = "" },
                    placeholder = { Text("Ingresa tu contraseña", color = AppColors.TextoSec, fontSize = 13.sp) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color(0xFFE0E0E0),
                        focusedBorderColor = AppColors.AzulClaro
                    )
                )

                // Mensaje de error
                if (error.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = error,
                        color = Color(0xFFD32F2F),
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Botón iniciar sesión
                Button(
                    onClick = {
                        if (correo.isBlank() || contrasena.isBlank()) {
                            error = "Por favor completa todos los campos."
                            return@Button
                        }
                        scope.launch {
                            cargando = true
                            val usuario = dao.login(correo.trim(), contrasena.trim())
                            cargando = false
                            if (usuario != null) {
                                prefs.guardarSesion(usuario.nombre, usuario.correo, usuario.rol)
                                val destino = if (usuario.rol == "Profesor")
                                    Routes.CURSOS_PROFESOR
                                else
                                    Routes.CURSOS_ESTUDIANTE
                                navController.navigate(destino) {
                                    popUpTo(Routes.LOGIN) { inclusive = true }
                                }
                            } else {
                                error = "Correo o contraseña incorrectos."
                            }
                        }
                    },
                    enabled = !cargando,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AppColors.AzulClaro)
                ) {
                    if (cargando) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                    } else {
                        Text(
                            text = "Iniciar sesión",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Olvidaste contraseña
        TextButton(onClick = {}) {
            Text(
                text = "¿Olvidaste tu contraseña?",
                color = AppColors.AzulClaro,
                fontSize = 13.sp
            )
        }

        // Ir a registro
        TextButton(onClick = { navController.navigate(Routes.REGISTER) }) {
            Text(
                text = "¿No tienes cuenta? Regístrate",
                color = AppColors.AzulClaro,
                fontSize = 13.sp
            )
        }
    }
}