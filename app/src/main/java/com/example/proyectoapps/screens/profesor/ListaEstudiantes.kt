package com.example.proyectoapps.screens.profesor

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectoapps.Estudiante
import com.example.proyectoapps.TopBarAzul
import com.example.proyectoapps.estudiantesEjemplo
import com.example.proyectoapps.ui.theme.AppColors

// PANTALLA 4 — LISTA DE ESTUDIANTES
// ─────────────────────────────────────────────
@Composable
fun PantallaEstudiantes(
    codigoCurso: String = "CS 101",
    estudiantes: List<Estudiante> = estudiantesEjemplo,
    navController: NavController
) {
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

        // Contador
        Text(
            text = "${estudiantes.size} ESTUDIANTES INSCRITOS",
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = AppColors.TextoSec,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(estudiantes) { estudiante ->
                TarjetaEstudiante(estudiante = estudiante)
            }
        }
    }
}

// ─────────────────────────────────────────────
// COMPONENTE — TARJETA DE ESTUDIANTE
// ─────────────────────────────────────────────
@Composable
fun TarjetaEstudiante(estudiante: Estudiante, modifier: Modifier = Modifier) {
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
            // Avatar ícono
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

            // Nombre y correo
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = estudiante.nombre,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.TextoPrin
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null,
                        tint = AppColors.TextoSec,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = estudiante.correo,
                        fontSize = 11.sp,
                        color = AppColors.TextoSec
                    )
                }
            }

            // Código
            Text(
                text = estudiante.codigo,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = AppColors.TextoSec
            )
        }
    }
}