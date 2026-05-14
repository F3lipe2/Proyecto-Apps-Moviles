package com.example.proyectoapps.screens.estudiante

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.proyectoapps.data.AppRepository

class EstudianteViewModelFactory(private val repository: AppRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EstudianteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EstudianteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
