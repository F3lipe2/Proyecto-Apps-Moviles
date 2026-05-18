package com.example.proyectoapps.screens.profesor

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectoapps.data.AppRepository
import com.example.proyectoapps.utils.NotificationHelper
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

@Composable
fun PantallaEscanerQR(
    codigoCurso: String,
    navController: NavController,
    viewModel: ProfesorViewModel = viewModel(factory = ProfesorViewModelFactory(AppRepository()))
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }
    val notificationHelper = remember { NotificationHelper(context) }
    val scope = rememberCoroutineScope()
    
    var hasCameraPermission by remember {
        mutableStateOf(ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
    }

    var hasNotificationPermission by remember {
        mutableStateOf(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
            } else {
                true
            }
        )
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted -> hasCameraPermission = granted }
    )

    val notificationLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted -> hasNotificationPermission = granted }
    )

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) cameraLauncher.launch(Manifest.permission.CAMERA)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !hasNotificationPermission) {
            notificationLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    var lastScannedId by remember { mutableStateOf<String?>(null) }
    var isPaused by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        Box(modifier = Modifier.fillMaxWidth().background(Color.Black).statusBarsPadding().padding(16.dp)) {
            Icon(Icons.Default.ArrowBack, "Volver", tint = Color.White, modifier = Modifier.clickable { navController.popBackStack() })
            Text("Escanear Alumno", color = Color.White, modifier = Modifier.align(Alignment.Center), fontWeight = FontWeight.Bold)
        }

        if (hasCameraPermission) {
            Box(modifier = Modifier.weight(1f)) {
                AndroidView(
                    factory = { ctx ->
                        val previewView = PreviewView(ctx)
                        val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                        cameraProviderFuture.addListener({
                            val cameraProvider = cameraProviderFuture.get()
                            val preview = Preview.Builder().build().also { it.setSurfaceProvider(previewView.surfaceProvider) }
                            val imageAnalysis = ImageAnalysis.Builder()
                                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                .build()
                            val barcodeScanner = BarcodeScanning.getClient()

                            imageAnalysis.setAnalyzer(cameraExecutor) { imageProxy ->
                                if (isPaused) {
                                    imageProxy.close()
                                    return@setAnalyzer
                                }

                                processImage(barcodeScanner, imageProxy) { rawValue ->
                                    if (rawValue != lastScannedId) {
                                        lastScannedId = rawValue
                                        isPaused = true
                                        
                                        scope.launch {
                                            // Buscamos el nombre real del estudiante en la base de datos
                                            val nombreEstudiante = viewModel.getNombreEstudiante(rawValue)
                                            viewModel.registrarAsistencia(rawValue, codigoCurso)
                                            notificationHelper.sendNotification(
                                                "Asistencia Registrada",
                                                "Alumno: $nombreEstudiante"
                                            )
                                            
                                            // Pausa de 3 segundos para evitar escaneos múltiples accidentales
                                            delay(3000)
                                            isPaused = false
                                        }
                                    }
                                }
                            }
                            cameraProvider.bindToLifecycle(lifecycleOwner, CameraSelector.DEFAULT_BACK_CAMERA, preview, imageAnalysis)
                        }, ContextCompat.getMainExecutor(ctx))
                        previewView
                    },
                    modifier = Modifier.fillMaxSize()
                )
                MarcoVisualScanner()
            }
        }
    }
}

@OptIn(ExperimentalGetImage::class)
private fun processImage(
    scanner: com.google.mlkit.vision.barcode.BarcodeScanner,
    imageProxy: ImageProxy,
    onSuccess: (String) -> Unit
) {
    val mediaImage = imageProxy.image
    if (mediaImage != null) {
        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        scanner.process(image)
            .addOnSuccessListener { barcodes ->
                if (barcodes.isNotEmpty()) {
                    barcodes[0].rawValue?.let { onSuccess(it) }
                }
            }
            .addOnCompleteListener { imageProxy.close() }
    } else {
        imageProxy.close()
    }
}

@Composable
fun MarcoVisualScanner() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(220.dp)) {
            val color = Color(0xFF2196F3)
            val stroke = 4.dp.toPx()
            val l = 28.dp.toPx()
            drawLine(color, Offset(0f, 0f), Offset(l, 0f), stroke)
            drawLine(color, Offset(0f, 0f), Offset(0f, l), stroke)
            drawLine(color, Offset(size.width, 0f), Offset(size.width - l, 0f), stroke)
            drawLine(color, Offset(size.width, 0f), Offset(size.width, l), stroke)
            drawLine(color, Offset(0f, size.height), Offset(l, size.height), stroke)
            drawLine(color, Offset(0f, size.height), Offset(0f, size.height - l), stroke)
            drawLine(color, Offset(size.width, size.height), Offset(size.width - l, size.height), stroke)
            drawLine(color, Offset(size.width, size.height), Offset(size.width, size.height - l), stroke)
        }
    }
}
