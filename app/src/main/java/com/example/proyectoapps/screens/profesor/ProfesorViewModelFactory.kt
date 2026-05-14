package com.example.proyectoapps.screens.profesor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.proyectoapps.data.AppRepository

class ProfesorViewModelFactory(private val repository: AppRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfesorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfesorViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
