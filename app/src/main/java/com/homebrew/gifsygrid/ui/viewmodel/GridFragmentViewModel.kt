package com.homebrew.gifsygrid.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.homebrew.gifsygrid.GifRemoteMediator
import com.homebrew.gifsygrid.db.AppDatabase
import com.homebrew.gifsygrid.db.GifEntity
import com.homebrew.gifsygrid.network.Service
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class GridFragmentViewModel(
    private val database: AppDatabase,
    private val apiService: Service,
    private val context: Context
) : ViewModel() {

    private val currentQuery = MutableStateFlow("cat")

    @OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
    val gifs: Flow<PagingData<GifEntity>> = currentQuery.flatMapLatest { query ->
        Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = GifRemoteMediator(query, database, apiService, context),
            pagingSourceFactory = {
                if (query.isEmpty()) {
                    database.gifDao().getAllGifs()
                } else {
                    database.gifDao().getGifs(query)
                }
            }
        ).flow.cachedIn(viewModelScope)
    }

    fun searchGifs(query: String) {
        currentQuery.value = query
    }
    fun markGifAsDeleted(gifId: String) {
        viewModelScope.launch {
            database.gifDao().markGifAsDeleted(gifId)
        }
    }
}