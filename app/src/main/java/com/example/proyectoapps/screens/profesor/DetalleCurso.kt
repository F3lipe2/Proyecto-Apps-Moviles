package com.example.proyectoapps.screens.profesor

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectoapps.data.AppRepository
import com.example.proyectoapps.data.local.Curso
import com.example.proyectoapps.navegation.Routes
import com.example.proyectoapps.ui.theme.AppColors
import com.example.proyectoapps.utils.SharedPrefsHelper
import com.google.firebase.auth.FirebaseAuth

@Composable
fun PantallaDetalleCurso(
    codigoCurso: String,
    navController: NavController,
    viewModel: ProfesorViewModel = viewModel(factory = ProfesorViewModelFactory(AppRepository()))
) {
    var curso by remember { mutableStateOf<Curso?>(null) }
    val context = LocalContext.current
    val prefs = remember { SharedPrefsHelper(context) }

    LaunchedEffect(codigoCurso) {
        curso = viewModel.getCursoPorCodigo(codigoCurso)
    }

    curso?.let { currentCurso ->
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
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White,
                            modifier = Modifier.clickable { navController.popBackStack() }
                        )
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = currentCurso.codigo,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = currentCurso.nombre,
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.85f)
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Salir",
                            tint = Color.White,
                            modifier = Modifier.clickable {
                                FirebaseAuth.getInstance().signOut()
                                prefs.cerrarSesion()
                                navController.navigate(Routes.LOGIN) {
                                    popUpTo(0)
                                }
                            }
                        )
                    }

                    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
                        Text(
                            text = currentCurso.nombre,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = currentCurso.descripcion,
                            fontSize = 13.sp,
                            color = Color.White.copy(alpha = 0.85f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ItemOpcionCurso(
                    titulo = "Tomar Asistencia",
                    subtitulo = "Escanear códigos QR de estudiantes",
                    colorIconoFondo = Color(0xFFE3F2FD),
                    colorIcono = AppColors.IconQR,
                    icono = Icons.Default.QrCodeScanner,
                    onClick = { navController.navigate(Routes.escanerQR(currentCurso.codigo)) }
                )
                ItemOpcionCurso(
                    titulo = "Ver Estudiantes",
                    subtitulo = "Lista de estudiantes inscritos",
                    colorIconoFondo = Color(0xFFE8F5E9),
                    colorIcono = AppColors.IconPerson,
                    icono = Icons.Default.Groups,
                    onClick = { navController.navigate(Routes.estudiantes(currentCurso.codigo)) }
                )
                ItemOpcionCurso(
                    titulo = "Historial de Asistencia",
                    subtitulo = "Ver todos los registros",
                    colorIconoFondo = Color(0xFFFFF3E0),
                    colorIcono = AppColors.IconHistory,
                    icono = Icons.Default.History,
                    onClick = { navController.navigate(Routes.historialProfesor(currentCurso.codigo)) }
                )
            }
        }
    } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Cargando...")
    }
}

@Composable
fun ItemOpcionCurso(
    titulo: String,
    subtitulo: String,
    colorIconoFondo: Color,
    colorIcono: Color,
    icono: ImageVector,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
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
                modifier = Modifier
                    .size(46.dp)
                    .background(colorIconoFondo, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icono,
                    contentDescription = titulo,
                    tint = colorIcono,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column {
                Text(
                    text = titulo,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.TextoPrin
                )
                Text(
                    text = subtitulo,
                    fontSize = 12.sp,
                    color = AppColors.TextoSec
                )
            }
        }
    }
}

@Composable
fun TopBarAzul(
    titulo: String,
    subtitulo: String,
    onVolver: () -> Unit = {},
    onSalir: () -> Unit = {}
) {
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
                .clickable { onVolver() }
        )
        Column(modifier = Modifier.align(Alignment.Center)) {
            Text(
                text = titulo,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Text(
                text = subtitulo,
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
                .clickable { onSalir() }
        )
    }
}
