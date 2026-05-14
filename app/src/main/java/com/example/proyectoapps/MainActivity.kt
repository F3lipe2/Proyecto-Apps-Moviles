package com.example.proyectoapps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.proyectoapps.navegation.NavGraph
import com.example.proyectoapps.ui.theme.ProyectoAppsTheme

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
