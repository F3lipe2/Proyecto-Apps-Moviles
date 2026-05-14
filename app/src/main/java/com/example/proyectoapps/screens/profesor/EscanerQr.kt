package com.example.proyectoapps.screens.profesor

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectoapps.data.AppRepository
import com.example.proyectoapps.ui.theme.AppColors
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors

@Composable
fun PantallaEscanerQR(
    codigoCurso: String,
    navController: NavController,
    viewModel: ProfesorViewModel = viewModel(factory = ProfesorViewModelFactory(AppRepository()))
) {
    val context = LocalContext.current
    
    // Observar el resultado del registro
    LaunchedEffect(Unit) {
        viewModel.registroExitoso.collect { exito ->
            if (exito) {
                Toast.makeText(context, "Asistencia registrada correctamente", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Error al registrar asistencia", Toast.LENGTH_LONG).show()
            }
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }
    
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCameraPermission = granted
            if (!granted) {
                Toast.makeText(context, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
            }
        }
    )

    LaunchedEffect(key1 = true) {
        if (!hasCameraPermission) {
            launcher.launch(Manifest.permission.CAMERA)
        }
    }

    var lastScannedId by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
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
        }

        if (hasCameraPermission) {
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                AndroidView(
                    factory = { ctx ->
                        val previewView = PreviewView(ctx)
                        val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)

                        cameraProviderFuture.addListener({
                            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
                            
                            val preview = Preview.Builder().build().also {
                                it.setSurfaceProvider(previewView.surfaceProvider)
                            }

                            val imageAnalysis = ImageAnalysis.Builder()
                                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                .build()

                            val barcodeScanner = BarcodeScanning.getClient()

                            imageAnalysis.setAnalyzer(cameraExecutor) { imageProxy ->
                                processImageProxy(barcodeScanner, imageProxy, codigoCurso, viewModel) { rawValue ->
                                    if (rawValue != lastScannedId) {
                                        lastScannedId = rawValue
                                    }
                                }
                            }

                            try {
                                cameraProvider.unbindAll()
                                cameraProvider.bindToLifecycle(
                                    lifecycleOwner,
                                    CameraSelector.DEFAULT_BACK_CAMERA,
                                    preview,
                                    imageAnalysis
                                )
                            } catch (exc: Exception) {
                                Log.e("Scanner", "Error al iniciar cámara", exc)
                            }
                        }, ContextCompat.getMainExecutor(ctx))
                        previewView
                    },
                    modifier = Modifier.fillMaxSize()
                )
                
                MarcoVisualScanner()
                
                Column(
                    modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Coloca el código QR del alumno dentro del marco",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "El registro se sincronizará con Firebase",
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.6f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = "Se requiere el permiso de cámara para poder escanear.",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = { launcher.launch(Manifest.permission.CAMERA) },
                        colors = ButtonDefaults.buttonColors(containerColor = AppColors.AzulClaro),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Conceder Permiso")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGetImage::class)
private fun processImageProxy(
    barcodeScanner: com.google.mlkit.vision.barcode.BarcodeScanner,
    imageProxy: ImageProxy,
    codigoCurso: String,
    viewModel: ProfesorViewModel,
    onSuccess: (String) -> Unit
) {
    val mediaImage = imageProxy.image
    if (mediaImage != null) {
        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        barcodeScanner.process(image)
            .addOnSuccessListener { barcodes ->
                if (barcodes.isNotEmpty()) {
                    val rawValue = barcodes[0].rawValue
                    if (rawValue != null) {
                        onSuccess(rawValue)
                        viewModel.registrarAsistencia(rawValue, codigoCurso)
                    }
                }
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    } else {
        imageProxy.close()
    }
}

@Composable
fun MarcoVisualScanner() {
    Box(
        modifier = Modifier.size(220.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val stroke = 4.dp.toPx()
            val corner = 28.dp.toPx()
            val color = Color(0xFF2196F3)

            drawLine(color, Offset(0f, corner), Offset(0f, 0f), stroke)
            drawLine(color, Offset(0f, 0f), Offset(corner, 0f), stroke)
            drawLine(color, Offset(size.width - corner, 0f), Offset(size.width, 0f), stroke)
            drawLine(color, Offset(size.width, 0f), Offset(size.width, corner), stroke)
            drawLine(color, Offset(0f, size.height - corner), Offset(0f, size.height), stroke)
            drawLine(color, Offset(0f, size.height), Offset(corner, size.height), stroke)
            drawLine(color, Offset(size.width - corner, size.height), Offset(size.width, size.height), stroke)
            drawLine(color, Offset(size.width, size.height - corner), Offset(size.width, size.height), stroke)
        }

        Icon(
            imageVector = Icons.Default.QrCodeScanner,
            contentDescription = null,
            tint = Color(0xFF2196F3).copy(alpha = 0.7f),
            modifier = Modifier.size(56.dp)
        )
    }
}
