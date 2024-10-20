package com.homebrew.gifsygrid.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homebrew.gifsygrid.network.Gif
import com.homebrew.gifsygrid.network.Service
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GridFragmentViewModel : ViewModel() {
    private val gifApiService = Service.create()


    private val gifFlow_ = MutableStateFlow<List<Gif>>(emptyList())
    val gifFlow: StateFlow<List<Gif>> = gifFlow_.asStateFlow()


    init {
        fetchGifs()  // Automatically fetch posts on ViewModel initialization
    }

    private fun fetchGifs() {
        viewModelScope.launch {
            try {
                val fetchedPosts = gifApiService.getGifBySearch(q="cat")
                gifFlow_.value = fetchedPosts.data

            } catch (e: Exception) {
                Log.e("GIPHY","Strong error")

            }
        }
    }

}