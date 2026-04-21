package com.example.proyectoapps.screens.profesor

import androidx.compose.foundation.Canvas
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectoapps.navegation.Routes

// PANTALLA 7 — ESCANER QR
// ─────────────────────────────────────────────
@Composable
fun PantallaEscanerQR(
    codigoCurso: String = "CS 101",
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // TopBar oscuro
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
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
                    text = "Escanear Código QR",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = codigoCurso,
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.7f),
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

        // Área de escaneo centrada
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Marco QR
                MarcoQR()

                Spacer(modifier = Modifier.height(36.dp))

                Text(
                    text = "Coloca el código QR dentro del marco",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "El código se escaneará automáticamente",
                    fontSize = 13.sp,
                    color = Color.White.copy(alpha = 0.6f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

// ─────────────────────────────────────────────
// COMPONENTE — MARCO QR CON ESQUINAS AZULES
// ─────────────────────────────────────────────
@Composable
fun MarcoQR(modifier: Modifier = Modifier) {
    val azulQR = Color(0xFF2196F3)
    val grosor = 4.dp
    val tamanoEsquina = 28.dp
    val tamanoMarco = 220.dp

    Box(
        modifier = modifier.size(tamanoMarco),
        contentAlignment = Alignment.Center
    ) {
        // Esquina superior izquierda
        Canvas(modifier = Modifier.fillMaxSize()) {
            val stroke = grosor.toPx()
            val corner = tamanoEsquina.toPx()
            val color = azulQR

            // Superior izquierda
            drawLine(color, Offset(0f, corner), Offset(0f, 0f), stroke)
            drawLine(color, Offset(0f, 0f), Offset(corner, 0f), stroke)

            // Superior derecha
            drawLine(color, Offset(size.width - corner, 0f), Offset(size.width, 0f), stroke)
            drawLine(color, Offset(size.width, 0f), Offset(size.width, corner), stroke)

            // Inferior izquierda
            drawLine(color, Offset(0f, size.height - corner), Offset(0f, size.height), stroke)
            drawLine(color, Offset(0f, size.height), Offset(corner, size.height), stroke)

            // Inferior derecha
            drawLine(color, Offset(size.width - corner, size.height), Offset(size.width, size.height), stroke)
            drawLine(color, Offset(size.width, size.height - corner), Offset(size.width, size.height), stroke)
        }

        // Ícono central
        Icon(
            imageVector = Icons.Default.QrCodeScanner,
            contentDescription = null,
            tint = azulQR.copy(alpha = 0.7f),
            modifier = Modifier.size(56.dp)
        )
    }
}