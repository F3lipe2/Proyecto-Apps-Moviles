package com.example.proyectoapps.screens.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectoapps.navegation.Routes
import com.example.proyectoapps.ui.theme.AppColors
import com.example.proyectoapps.utils.SharedPrefsHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

@Composable
fun PantallaRegister(navController: NavController) {

    val context  = LocalContext.current
    val prefs    = remember { SharedPrefsHelper(context) }
    val scope    = rememberCoroutineScope()
    
    val auth = remember { FirebaseAuth.getInstance() }
    val firestore = remember { FirebaseFirestore.getInstance() }

    var nombre          by remember { mutableStateOf("") }
    var correo          by remember { mutableStateOf("") }
    var contrasena      by remember { mutableStateOf("") }
    var confirmar       by remember { mutableStateOf("") }
    var rolSeleccionado by remember { mutableStateOf("Estudiante") }
    var error           by remember { mutableStateOf("") }
    var cargando        by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.GrisFondo)
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(modifier = Modifier.height(40.dp))

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

        Text(
            text = "Crear cuenta",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.TextoPrin
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Completa tus datos para registrarte",
            fontSize = 14.sp,
            color = AppColors.TextoSec
        )

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = AppColors.GrisTarjeta),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {

                Text(text = "Nombre completo", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = AppColors.TextoPrin)
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it; error = "" },
                    placeholder = { Text("Tu nombre completo", color = AppColors.TextoSec, fontSize = 13.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color(0xFFE0E0E0),
                        focusedBorderColor = AppColors.AzulClaro
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Correo electrónico", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = AppColors.TextoPrin)
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

                Text(text = "Contraseña", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = AppColors.TextoPrin)
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = contrasena,
                    onValueChange = { contrasena = it; error = "" },
                    placeholder = { Text("Crea una contraseña", color = AppColors.TextoSec, fontSize = 13.sp) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color(0xFFE0E0E0),
                        focusedBorderColor = AppColors.AzulClaro
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Confirmar contraseña", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = AppColors.TextoPrin)
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = confirmar,
                    onValueChange = { confirmar = it; error = "" },
                    placeholder = { Text("Repite tu contraseña", color = AppColors.TextoSec, fontSize = 13.sp) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color(0xFFE0E0E0),
                        focusedBorderColor = AppColors.AzulClaro
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(text = "Rol", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = AppColors.TextoPrin)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    listOf("Estudiante", "Profesor").forEach { rol ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = rolSeleccionado == rol,
                                onClick = { rolSeleccionado = rol },
                                colors = RadioButtonDefaults.colors(selectedColor = AppColors.AzulClaro)
                            )
                            Text(text = rol, fontSize = 13.sp, color = AppColors.TextoPrin)
                        }
                    }
                }

                if (error.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = error, color = Color(0xFFD32F2F), fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        when {
                            nombre.isBlank() || correo.isBlank() || contrasena.isBlank() -> {
                                error = "Por favor completa todos los campos."
                                return@Button
                            }
                            contrasena != confirmar -> {
                                error = "Las contraseñas no coinciden."
                                return@Button
                            }
                            contrasena.length < 6 -> {
                                error = "La contraseña debe tener al menos 6 caracteres."
                                return@Button
                            }
                        }
                        
                        cargando = true
                        auth.createUserWithEmailAndPassword(correo.trim(), contrasena.trim())
                            .addOnSuccessListener { result ->
                                val uid = result.user?.uid ?: ""
                                val userMap = hashMapOf(
                                    "nombre" to nombre.trim(),
                                    "correo" to correo.trim(),
                                    "rol" to rolSeleccionado
                                )
                                
                                firestore.collection("users").document(uid)
                                    .set(userMap)
                                    .addOnSuccessListener {
                                        prefs.guardarSesion(uid, nombre.trim(), correo.trim(), rolSeleccionado)
                                        cargando = false
                                        val destino = if (rolSeleccionado == "Profesor")
                                            Routes.CURSOS_PROFESOR
                                        else
                                            Routes.CURSOS_ESTUDIANTE
                                        navController.navigate(destino) {
                                            popUpTo(Routes.LOGIN) { inclusive = true }
                                        }
                                    }
                                    .addOnFailureListener { e ->
                                        cargando = false
                                        error = e.message ?: "Error al guardar perfil"
                                        // Si falla Firestore, eliminamos el usuario de Auth para permitir reintentar
                                        result.user?.delete()
                                    }
                            }
                            .addOnFailureListener { e ->
                                cargando = false
                                error = e.message ?: "Error en registro"
                            }
                    },
                    enabled = !cargando,
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AppColors.AzulClaro)
                ) {
                    if (cargando) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                    } else {
                        Text(text = "Crear cuenta", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { navController.popBackStack() }) {
            Text(
                text = "¿Ya tienes cuenta? Inicia sesión",
                color = AppColors.AzulClaro,
                fontSize = 13.sp
            )
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}
