package com.homebrew.gifsygrid.ui.viewmodel


import androidx.lifecycle.ViewModel
import com.homebrew.gifsygrid.db.GifEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedViewModel :ViewModel() {
    private val _gifList = MutableStateFlow<List<GifEntity>>(emptyList())

    val gifList: StateFlow<List<GifEntity>> = _gifList.asStateFlow()

    fun updateGifList(newGifList: List<GifEntity>) {
        _gifList.value = newGifList
    }
}