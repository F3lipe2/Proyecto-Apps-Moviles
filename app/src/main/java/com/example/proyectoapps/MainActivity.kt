package com.example.proyectoapps

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import com.example.proyectoapps.navegation.NavGraph
import com.example.proyectoapps.ui.theme.ProyectoAppsTheme
import com.example.proyectoapps.utils.NotificationHelper
import com.example.proyectoapps.utils.SharedPrefsHelper
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.DocumentChange

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        val notificationHelper = NotificationHelper(this)
        val prefs = SharedPrefsHelper(this)

        setContent {
            ProyectoAppsTheme {
                // Launcher para permiso de notificaciones (Android 13+)
                val permissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { }
                )

                // Listener en tiempo real para el Estudiante
                LaunchedEffect(Unit) {
                    // Pedir permiso si es necesario
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }

                    val uid = prefs.getUserId()
                    val rol = prefs.getRol()
                    
                    if (uid.isNotEmpty() && rol == "Estudiante") {
                        val db = FirebaseFirestore.getInstance()
                        db.collection("asistencias")
                            .whereEqualTo("idEstudiante", uid)
                            .addSnapshotListener { snapshots, e ->
                                if (e != null) return@addSnapshotListener
                                
                                for (dc in snapshots!!.documentChanges) {
                                    if (dc.type == DocumentChange.Type.ADDED) {
                                        val curso = dc.document.getString("codigoCurso") ?: "un curso"
                                        notificationHelper.notifySuccess(
                                            "¡Asistencia Confirmada!",
                                            "Has sido registrado exitosamente en: $curso"
                                        )
                                    }
                                }
                            }
                    }
                }

                NavGraph()
            }
        }
    }
}
