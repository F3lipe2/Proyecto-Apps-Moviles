package com.example.proyectoapps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectoapps.navegation.NavGraph
import com.example.proyectoapps.ui.theme.AppColors
import com.example.proyectoapps.ui.theme.ProyectoAppsTheme
import com.example.proyectoapps.ui.theme.coloresDisponibles

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProyectoAppsTheme {
                NavGraph()
            }
        }
    }
}

@Composable
fun Greeting() {
}

// MODELOS DE DATOS (solo para previsualización)
// ─────────────────────────────────────────────
data class Curso(
    val nombre: String,
    val codigo: String,
    val descripcion: String,
    val estudiantes: Int,
    val sesiones: Int,
    val color: Color
)

data class OpcionDetalle(
    val titulo: String,
    val subtitulo: String,
    val icono: ImageVector,
    val colorIcono: Color,
    val colorFondoIcono: Color
)

data class Estudiante(
    val nombre: String,
    val correo: String,
    val codigo: String
)

data class RegistroAsistencia(
    val fecha: String,
    val presente: Boolean
)

data class AsistenciaEstudiante(
    val estudiante: Estudiante,
    val registros: List<RegistroAsistencia>,
    val totalClases: Int
)

val estudiantesEjemplo = listOf(
    Estudiante("Alice Johnson",  "alice.johnson@university.edu",  "STU001"),
    Estudiante("Bob Smith",      "bob.smith@university.edu",      "STU002"),
    Estudiante("Carol Williams", "carol.williams@university.edu", "STU003"),
    Estudiante("David Brown",    "david.brown@university.edu",    "STU004")
)

val asistenciasEjemplo = listOf(
    AsistenciaEstudiante(
        estudiante = estudiantesEjemplo[0],
        registros = listOf(
            RegistroAsistencia("4 mar", false),
            RegistroAsistencia("2 mar", true),
            RegistroAsistencia("28 feb", true)
        ),
        totalClases = 3
    ),
    AsistenciaEstudiante(
        estudiante = estudiantesEjemplo[1],
        registros = emptyList(),
        totalClases = 3
    ),
    AsistenciaEstudiante(
        estudiante = estudiantesEjemplo[2],
        registros = emptyList(),
        totalClases = 3
    ),
    AsistenciaEstudiante(
        estudiante = estudiantesEjemplo[3],
        registros = emptyList(),
        totalClases = 3
    )
)

data class CursoEstudiante(
    val nombre: String,
    val codigo: String,
    val descripcion: String,
    val estudiantes: Int,
    val sesiones: Int,
    val color: Color
)

data class RegistroClase(
    val fecha: String,
    val presente: Boolean
)

val cursosEstudianteEjemplo = listOf(
    CursoEstudiante("Introduction to Computer Science", "CS 101", "Fundamentals of programming and computer science", 24, 32, AppColors.Azul),
    CursoEstudiante("Data Structures and Algorithms",   "CS 201", "Advanced data structures and algorithm design",    24, 32, AppColors.Verde),
    CursoEstudiante("Database Management Systems",      "CS 301", "Relational databases and SQL",                     24, 32, AppColors.Rojo),
    CursoEstudiante("Mobile Application Development",  "CS 401", "Android development with Kotlin",                  24, 32, AppColors.Morado)
)

val registrosEjemplo = listOf(
    RegistroClase("miércoles, 4 de mar 2026", false),
    RegistroClase("lunes, 2 de mar 2026",     true),
    RegistroClase("sábado, 28 de feb 2026",   true)
)

// ─────────────────────────────────────────────
// DATOS DE MUESTRA
// ─────────────────────────────────────────────
val cursosEjemplo = listOf(
    Curso("Introduction to Computer Science", "CS 101", "Fundamentals of programming and computer science", 24, 32, AppColors.Azul),
    Curso("Data Structures and Algorithms",   "CS 201", "Advanced data structures and algorithm design",    24, 32, AppColors.Verde),
    Curso("Database Management Systems",      "CS 301", "Relational databases and SQL",                     24, 32, AppColors.Rojo),
    Curso("Mobile Application Development",  "CS 401", "Android development with Kotlin",                  24, 32, AppColors.Morado)
)

// ─────────────────────────────────────────────
// PANTALLA 1 — LOGIN
// ─────────────────────────────────────────────
@Composable
fun PantallaLogin() {
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
                imageVector = Icons.Default.Add, // Reemplaza con el ícono de graduación si lo tienes
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
                    value = "",
                    onValueChange = {},
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
                    value = "",
                    onValueChange = {},
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

                Spacer(modifier = Modifier.height(20.dp))

                // Botón iniciar sesión
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AppColors.AzulClaro)
                ) {
                    Text(
                        text = "Iniciar sesión",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
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
    }
}

// ─────────────────────────────────────────────
// PANTALLA 2 — MIS CURSOS
// ─────────────────────────────────────────────
@Composable
fun PantallaMisCursos(
    cursos: List<Curso> = cursosEjemplo,
    nombreProfesor: String = "Dr. Sarah Miller"
) {
    Scaffold(
        // TopBar azul
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
                        .clickable {}
                )
            }
        },
        // FAB para añadir cursos
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(cursos) { curso ->
                TarjetaCurso(curso = curso)
            }
        }
    }
}

// ─────────────────────────────────────────────
// COMPONENTE — TARJETA DE CURSO
// ─────────────────────────────────────────────
@Composable
fun TarjetaCurso(curso: Curso, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {},
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.GrisTarjeta),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column {
            // Encabezado de color
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(curso.color)
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

            // Cuerpo de la tarjeta
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                Text(
                    text = curso.descripcion,
                    fontSize = 13.sp,
                    color = AppColors.TextoSec
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(
                        text = "👥 ${curso.estudiantes} estudiantes",
                        fontSize = 12.sp,
                        color = AppColors.TextoSec
                    )
                    Text(
                        text = "📖 ${curso.sesiones} sesiones",
                        fontSize = 12.sp,
                        color = AppColors.TextoSec
                    )
                }
            }
        }
    }
}

// ─────────────────────────────────────────────
// PANTALLA 3 — DETALLE DE CURSO
// ─────────────────────────────────────────────
@Composable
fun PantallaDetalleCurso(
    curso: Curso = cursosEjemplo.first()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.GrisFondo)
    ) {
        // TopBar con fondo azul que incluye el encabezado del curso
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppColors.AzulClaro)
                .statusBarsPadding()
        ) {
            Column {
                // Fila de navegación
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
                        modifier = Modifier.clickable {}
                    )
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = curso.codigo,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = curso.nombre,
                            fontSize = 11.sp,
                            color = Color.White.copy(alpha = 0.85f)
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Salir",
                        tint = Color.White,
                        modifier = Modifier.clickable {}
                    )
                }

                // Título grande del curso
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
                    Text(
                        text = curso.nombre,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = curso.descripcion,
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Opciones del curso
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ItemOpcionCurso(
                titulo = "Tomar Asistencia",
                subtitulo = "Escanear códigos QR de estudiantes",
                colorIconoFondo = Color(0xFFE3F2FD),
                colorIcono = AppColors.IconQR,
                icono = Icons.Default.Add // Reemplaza con ícono QR real
            )
            ItemOpcionCurso(
                titulo = "Ver Estudiantes",
                subtitulo = "${curso.estudiantes} estudiantes inscritos",
                colorIconoFondo = Color(0xFFE8F5E9),
                colorIcono = AppColors.IconPerson,
                icono = Icons.Default.Add // Reemplaza con ícono persona real
            )
            ItemOpcionCurso(
                titulo = "Historial de Asistencia",
                subtitulo = "Ver todos los registros",
                colorIconoFondo = Color(0xFFFFF3E0),
                colorIcono = AppColors.IconHistory,
                icono = Icons.Default.Add // Reemplaza con ícono historial real
            )
        }
    }
}

// ─────────────────────────────────────────────
// COMPONENTE — ÍTEM DE OPCIÓN EN DETALLE CURSO
// ─────────────────────────────────────────────
@Composable
fun ItemOpcionCurso(
    titulo: String,
    subtitulo: String,
    colorIconoFondo: Color,
    colorIcono: Color,
    icono: ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {},
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
            // Ícono con fondo de color
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

            // Textos
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


// ─────────────────────────────────────────────
// COMPONENTE REUTILIZABLE — TOP BAR AZUL
// ─────────────────────────────────────────────
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

// ─────────────────────────────────────────────
// PANTALLA 4 — LISTA DE ESTUDIANTES
// ─────────────────────────────────────────────
@Composable
fun PantallaEstudiantes(
    codigoCurso: String = "CS 101",
    estudiantes: List<Estudiante> = estudiantesEjemplo
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.GrisFondo)
    ) {
        TopBarAzul(titulo = "Estudiantes", subtitulo = codigoCurso)

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

// ─────────────────────────────────────────────
// PANTALLA 5 — HISTORIAL DE ASISTENCIA
// ─────────────────────────────────────────────
@Composable
fun PantallaHistorialAsistencia(
    codigoCurso: String = "CS 101",
    asistencias: List<AsistenciaEstudiante> = asistenciasEjemplo
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.GrisFondo)
    ) {
        TopBarAzul(titulo = "Historial de Asistencia", subtitulo = codigoCurso)

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(asistencias) { asistencia ->
                TarjetaAsistenciaEstudiante(asistencia = asistencia)
            }
        }
    }
}

// ─────────────────────────────────────────────
// COMPONENTE — TARJETA ASISTENCIA CON EXPANSIÓN
// ─────────────────────────────────────────────
@Composable
fun TarjetaAsistenciaEstudiante(
    asistencia: AsistenciaEstudiante,
    modifier: Modifier = Modifier
) {
    val presentes = asistencia.registros.count { it.presente }
    val porcentaje = if (asistencia.totalClases > 0)
        (presentes * 100) / asistencia.totalClases else 0
    val expandida = asistencia.registros.isNotEmpty()

    val colorPorcentaje = when {
        porcentaje >= 80 -> Color(0xFF388E3C)
        porcentaje >= 50 -> Color(0xFFF57C00)
        else             -> Color(0xFFD32F2F)
    }
    val fondoPorcentaje = when {
        porcentaje >= 80 -> Color(0xFFE8F5E9)
        porcentaje >= 50 -> Color(0xFFFFF3E0)
        else             -> Color(0xFFFFEBEE)
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.GrisTarjeta),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Fila principal
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar con inicial
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFFE3F2FD), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = asistencia.estudiante.nombre.first().toString(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.AzulClaro
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = asistencia.estudiante.nombre,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AppColors.TextoPrin
                    )
                    Text(
                        text = "$presentes / ${asistencia.totalClases} clases",
                        fontSize = 12.sp,
                        color = colorPorcentaje
                    )
                }

                // Badge de porcentaje
                Box(
                    modifier = Modifier
                        .background(fondoPorcentaje, RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "$porcentaje%",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorPorcentaje
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    imageVector = if (expandida) Icons.Default.KeyboardArrowUp
                    else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = AppColors.TextoSec,
                    modifier = Modifier.size(20.dp)
                )
            }

            // Registros expandidos
            if (expandida) {
                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(color = Color(0xFFEEEEEE))
                Spacer(modifier = Modifier.height(8.dp))
                asistencia.registros.forEach { registro ->
                    FilaRegistroAsistencia(registro = registro)
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        }
    }
}

// ─────────────────────────────────────────────
// COMPONENTE — FILA DE REGISTRO (fecha + estado)
// ─────────────────────────────────────────────
@Composable
fun FilaRegistroAsistencia(registro: RegistroAsistencia) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = registro.fecha,
            fontSize = 13.sp,
            color = AppColors.TextoSec
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = if (registro.presente) Icons.Default.CheckCircle
                else Icons.Default.Cancel,
                contentDescription = null,
                tint = if (registro.presente) Color(0xFF388E3C) else Color(0xFFD32F2F),
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = if (registro.presente) "Presente" else "Ausente",
                fontSize = 13.sp,
                color = if (registro.presente) Color(0xFF388E3C) else Color(0xFFD32F2F)
            )
        }
    }
}

// ─────────────────────────────────────────────
// PANTALLA 6 — NUEVO CURSO
// ─────────────────────────────────────────────
@Composable
fun PantallaNuevoCurso(
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
                        modifier = Modifier.clickable {}
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
                        modifier = Modifier.clickable {}
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
                onClick = {},
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

// ─────────────────────────────────────────────
// PANTALLA 7 — ESCANER QR
// ─────────────────────────────────────────────
@Composable
fun PantallaEscanerQR(
    codigoCurso: String = "CS 101"
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
                    .clickable {}
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
                    .clickable {}
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



// PANTALLA 1 — MIS CURSOS (ESTUDIANTE)
// La diferencia con la del profesor es que el
// FAB muestra el código QR del estudiante
// ─────────────────────────────────────────────
@Composable
fun PantallaMisCursosEstudiante(
    cursos: List<CursoEstudiante> = cursosEstudianteEjemplo,
    nombreEstudiante: String = "Alice Johnson"
) {
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
                        text = nombreEstudiante,
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
                        .clickable {}
                )
            }
        },
        // FAB con ícono QR en lugar del "+"
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                containerColor = AppColors.AzulClaro,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.QrCode,
                    contentDescription = "Mi código QR",
                    tint = Color.White
                )
            }
        },
        containerColor = AppColors.GrisFondo
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(cursos) { curso ->
                TarjetaCursoEstudiante(curso = curso)
            }
        }
    }
}

// ─────────────────────────────────────────────
// COMPONENTE — TARJETA DE CURSO (ESTUDIANTE)
// ─────────────────────────────────────────────
@Composable
fun TarjetaCursoEstudiante(
    curso: CursoEstudiante,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {},
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.GrisTarjeta),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column {
            // Encabezado de color
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(curso.color)
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
            // Cuerpo
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                Text(
                    text = curso.descripcion,
                    fontSize = 13.sp,
                    color = AppColors.TextoSec
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(
                        text = "👥 ${curso.estudiantes} estudiantes",
                        fontSize = 12.sp,
                        color = AppColors.TextoSec
                    )
                    Text(
                        text = "📖 ${curso.sesiones} sesiones",
                        fontSize = 12.sp,
                        color = AppColors.TextoSec
                    )
                }
            }
        }
    }
}

// ─────────────────────────────────────────────
// PANTALLA 2 — DETALLE DEL CURSO (ESTUDIANTE)
// Solo tiene Ver Estudiantes e Historial,
// no tiene Tomar Asistencia
// ─────────────────────────────────────────────
@Composable
fun PantallaDetalleCursoEstudiante(
    curso: CursoEstudiante = cursosEstudianteEjemplo.first()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.GrisFondo)
    ) {
        // TopBar expandido azul
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppColors.AzulClaro)
                .statusBarsPadding()
        ) {
            Column {
                // Fila de navegación
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
                        modifier = Modifier.clickable {}
                    )
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = curso.codigo,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = curso.nombre,
                            fontSize = 11.sp,
                            color = Color.White.copy(alpha = 0.85f)
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Salir",
                        tint = Color.White,
                        modifier = Modifier.clickable {}
                    )
                }
                // Título grande
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
                    Text(
                        text = curso.nombre,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = curso.descripcion,
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Solo 2 opciones para el estudiante
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ItemOpcionEstudiante(
                titulo = "Ver Estudiantes",
                subtitulo = "${curso.estudiantes} estudiantes inscritos",
                colorFondoIcono = Color(0xFFE8F5E9),
                colorIcono = AppColors.IconPerson,
                icono = Icons.Default.Group
            )
            ItemOpcionEstudiante(
                titulo = "Historial de Asistencia",
                subtitulo = "Ver todos los registros",
                colorFondoIcono = Color(0xFFFFF3E0),
                colorIcono = AppColors.IconHistory,
                icono = Icons.Default.Assignment
            )
        }
    }
}

// ─────────────────────────────────────────────
// COMPONENTE — ÍTEM DE OPCIÓN (ESTUDIANTE)
// ─────────────────────────────────────────────
@Composable
fun ItemOpcionEstudiante(
    titulo: String,
    subtitulo: String,
    colorFondoIcono: Color,
    colorIcono: Color,
    icono: ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {},
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
                    .background(colorFondoIcono, RoundedCornerShape(10.dp)),
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

// ─────────────────────────────────────────────
// PANTALLA 3 — MI CÓDIGO QR
// ─────────────────────────────────────────────
@Composable
fun PantallaMiCodigoQR(
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
                    .clickable {}
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
                    .clickable {}
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

// ─────────────────────────────────────────────
// PANTALLA 4 — HISTORIAL DE ASISTENCIA (ESTUDIANTE)
// Diferente al del profesor: muestra resumen
// personal con porcentaje, presentes, ausentes
// y clases totales, luego lista de registros
// ─────────────────────────────────────────────
@Composable
fun PantallaHistorialEstudiante(
    codigoCurso: String = "CS 101",
    registros: List<RegistroClase> = registrosEjemplo
) {
    val totalClases  = registros.size
    val presentes    = registros.count { it.presente }
    val ausentes     = registros.count { !it.presente }
    val porcentaje   = if (totalClases > 0) (presentes * 100) / totalClases else 0

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
                    .clickable {}
            )
            Column(modifier = Modifier.align(Alignment.Center)) {
                Text(
                    text = "Historial de Asistencia",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = codigoCurso,
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.85f)
                )
            }
            Icon(
                imageVector = Icons.Default.ExitToApp,
                contentDescription = "Salir",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable {}
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tarjeta resumen de asistencia
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = AppColors.GrisTarjeta),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Mi Asistencia",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.TextoPrin
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    EstadisticaAsistencia(
                        valor = "$porcentaje%",
                        etiqueta = "Asistencia",
                        color = AppColors.TextoPrin
                    )
                    EstadisticaAsistencia(
                        valor = "$presentes",
                        etiqueta = "Presentes",
                        color = Color(0xFF388E3C)
                    )
                    EstadisticaAsistencia(
                        valor = "$ausentes",
                        etiqueta = "Ausentes",
                        color = Color(0xFFD32F2F)
                    )
                    EstadisticaAsistencia(
                        valor = "$totalClases",
                        etiqueta = "Clases",
                        color = AppColors.TextoPrin
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de registros individuales
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(registros) { registro ->
                TarjetaRegistroClase(registro = registro)
            }
        }
    }
}

// ─────────────────────────────────────────────
// COMPONENTE — ESTADÍSTICA INDIVIDUAL
// (porcentaje, presentes, ausentes, clases)
// ─────────────────────────────────────────────
@Composable
fun EstadisticaAsistencia(
    valor: String,
    etiqueta: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = valor,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = etiqueta,
            fontSize = 11.sp,
            color = AppColors.TextoSec
        )
    }
}

// ─────────────────────────────────────────────
// COMPONENTE — TARJETA DE REGISTRO DE CLASE
// ─────────────────────────────────────────────
@Composable
fun TarjetaRegistroClase(
    registro: RegistroClase,
    modifier: Modifier = Modifier
) {
    val colorEstado  = if (registro.presente) Color(0xFF388E3C) else Color(0xFFD32F2F)
    val fondoIcono   = if (registro.presente) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
    val iconoEstado  = if (registro.presente) Icons.Default.CheckCircle else Icons.Default.Cancel
    val textoEstado  = if (registro.presente) "Presente" else "Ausente"

    Card(
        modifier = modifier.fillMaxWidth(),
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
            // Ícono de estado
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(fondoIcono, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = iconoEstado,
                    contentDescription = textoEstado,
                    tint = colorEstado,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Fecha
            Text(
                text = registro.fecha,
                fontSize = 14.sp,
                color = AppColors.TextoPrin,
                modifier = Modifier.weight(1f)
            )

            // Estado
            Text(
                text = textoEstado,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorEstado
            )
        }
    }
}

// ─────────────────────────────────────────────
// PREVIEWS
// ─────────────────────────────────────────────
@Preview(showBackground = true, showSystemUi = true, name = "Estudiantes")
@Composable
fun PantallaEstudiantesPreview() {
    PantallaEstudiantes()
}

@Preview(showBackground = true, showSystemUi = true, name = "Historial")
@Composable
fun PantallaHistorialPreview() {
    PantallaHistorialAsistencia()
}

@Preview(showBackground = true, showSystemUi = true, name = "Nuevo Curso")
@Composable
fun PantallaNuevoCursoPreview() {
    PantallaNuevoCurso()
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Escaner QR",
    backgroundColor = 0xFF000000
)
@Composable
fun PantallaEscanerQRPreview() {
    PantallaEscanerQR()
}

@Preview(showBackground = true, showSystemUi = true, name = "Login")
@Composable
fun PantallaLoginPreview() {
    PantallaLogin()
}

@Preview(showBackground = true, showSystemUi = true, name = "Mis Cursos")
@Composable
fun PantallaMisCursosPreview() {
    PantallaMisCursos()
}

@Preview(showBackground = true, showSystemUi = true, name = "Detalle Curso")
@Composable
fun PantallaDetalleCursoPreview() {
    PantallaDetalleCurso()
}

@Preview(showBackground = true, showSystemUi = true, name = "Mis Cursos Estudiante")
@Composable
fun PantallaMisCursosEstudiantePreview() {
    PantallaMisCursosEstudiante()
}

@Preview(showBackground = true, showSystemUi = true, name = "Detalle Curso Estudiante")
@Composable
fun PantallaDetalleCursoEstudiantePreview() {
    PantallaDetalleCursoEstudiante()
}

@Preview(showBackground = true, showSystemUi = true, name = "Mi Código QR")
@Composable
fun PantallaMiCodigoQRPreview() {
    PantallaMiCodigoQR()
}

@Preview(showBackground = true, showSystemUi = true, name = "Historial Estudiante")
@Composable
fun PantallaHistorialEstudiantePreview() {
    PantallaHistorialEstudiante()
}
