package com.example.proyectoapps.screens.profesor

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectoapps.navegation.Routes
import com.example.proyectoapps.ui.theme.AppColors
import com.example.proyectoapps.ui.theme.coloresDisponibles

// PANTALLA 6 — NUEVO CURSO
// ─────────────────────────────────────────────
@Composable
fun PantallaNuevoCurso(
    navController: NavController,
    codigoCurso: String = "CS 101"
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.GrisFondo)
    ) {
        // TopBar con preview del curso
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppColors.AzulClaro)
                .statusBarsPadding()
        ) {
            Column {
                // Fila navegación
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
                        modifier = Modifier.clickable { navController.navigate(Routes.LOGIN) }
                    )
                }

                // Preview nombre/código
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
                            text = "Nombre del curso",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                        Text(
                            text = "CÓDIGO",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }

        // Formulario
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Nombre del curso
            CampoFormulario(
                etiqueta = "Nombre del curso",
                placeholder = "Ej. Introducción a la Programación",
                obligatorio = true
            )

            // Código del curso
            CampoFormulario(
                etiqueta = "Código del curso",
                placeholder = "EJ. CS 101",
                obligatorio = true
            )

            // Descripción
            Column {
                Row {
                    Text(
                        text = "Descripción",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = AppColors.TextoPrin
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "(opcional)",
                        fontSize = 13.sp,
                        color = AppColors.TextoSec
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = {
                        Text(
                            "Describe brevemente el contenido del curso...",
                            color = AppColors.TextoSec,
                            fontSize = 13.sp
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color(0xFFE0E0E0),
                        focusedBorderColor = AppColors.AzulClaro
                    )
                )
                Text(
                    text = "0/120",
                    fontSize = 11.sp,
                    color = AppColors.TextoSec,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 4.dp)
                )
            }

            // Selector de color
            Column {
                Text(
                    text = "Color del curso",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = AppColors.TextoPrin
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    coloresDisponibles.forEachIndexed { index, color ->
                        SelectorColor(
                            color = color,
                            seleccionado = index == 0 // Primero seleccionado por defecto
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Botón crear
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.AzulClaro)
            ) {
                Text(
                    text = "Crear Curso",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }
    }
}

// ─────────────────────────────────────────────
// COMPONENTE — CAMPO DE FORMULARIO REUTILIZABLE
// ─────────────────────────────────────────────
@Composable
fun CampoFormulario(
    etiqueta: String,
    placeholder: String,
    obligatorio: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row {
            Text(
                text = etiqueta,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = AppColors.TextoPrin
            )
            if (obligatorio) {
                Spacer(modifier = Modifier.width(2.dp))
                Text(text = "*", fontSize = 13.sp, color = Color(0xFFD32F2F))
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text(placeholder, color = AppColors.TextoSec, fontSize = 13.sp) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFE0E0E0),
                focusedBorderColor = AppColors.AzulClaro
            )
        )
    }
}

// ─────────────────────────────────────────────
// COMPONENTE — CÍRCULO SELECTOR DE COLOR
// ─────────────────────────────────────────────
@Composable
fun SelectorColor(
    color: Color,
    seleccionado: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(36.dp)
            .background(color, CircleShape)
            .clickable {},
        contentAlignment = Alignment.Center
    ) {
        if (seleccionado) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Seleccionado",
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}