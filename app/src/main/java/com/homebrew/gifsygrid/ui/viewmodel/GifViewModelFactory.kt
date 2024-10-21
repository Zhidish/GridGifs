package com.homebrew.gifsygrid.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.homebrew.gifsygrid.db.AppDatabase
import com.homebrew.gifsygrid.network.Service

class GifViewModelFactory(
    private val database: AppDatabase,
    private val apiService: Service,
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GridFragmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GridFragmentViewModel(database, apiService, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}