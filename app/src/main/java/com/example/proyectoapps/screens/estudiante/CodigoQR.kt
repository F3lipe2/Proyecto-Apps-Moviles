package com.example.proyectoapps.screens.estudiante

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCode2
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
import com.example.proyectoapps.navegation.Routes
import com.example.proyectoapps.ui.theme.AppColors

// PANTALLA 3 — MI CÓDIGO QR
// ─────────────────────────────────────────────
@Composable
fun PantallaMiCodigoQR(
    navController: NavController,
    nombre: String = "Alice Johnson",
    correo: String = "alumno@universidad.edu",
    idEstudiante: String = "STU001"
) {
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
                    text = "Mi Código QR",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = nombre,
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.85f),
                    textAlign = TextAlign.Center
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

        Spacer(modifier = Modifier.height(24.dp))

        // Tarjeta de perfil
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = AppColors.GrisTarjeta),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .background(AppColors.AzulClaro, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = nombre,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.TextoPrin
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = correo,
                    fontSize = 13.sp,
                    color = AppColors.TextoSec
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "ID: $idEstudiante",
                    fontSize = 13.sp,
                    color = AppColors.TextoSec
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tarjeta del código QR
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = AppColors.GrisTarjeta),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Imagen QR de placeholder
                // Cuando tengas la librería de QR reemplaza este Box por el componente real
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.QrCode2,
                        contentDescription = "Código QR",
                        tint = AppColors.TextoPrin,
                        modifier = Modifier.size(160.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Muestra este código a tu docente para registrar asistencia",
                    fontSize = 14.sp,
                    color = AppColors.TextoPrin,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Mantén esta pantalla visible durante el pase de lista",
                    fontSize = 12.sp,
                    color = AppColors.TextoSec,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}