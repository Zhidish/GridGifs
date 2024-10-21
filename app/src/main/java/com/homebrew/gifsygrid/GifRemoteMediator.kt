package com.homebrew.gifsygrid

import android.content.Context
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.homebrew.gifsygrid.db.AppDatabase
import com.homebrew.gifsygrid.db.GifEntity
import com.homebrew.gifsygrid.network.Service
import retrofit2.HttpException
import java.io.File
import java.io.IOException


@OptIn(ExperimentalPagingApi::class)
class GifRemoteMediator(
    private val query: String,
    private val database: AppDatabase,
    private val apiService: Service,
    private val context: Context
) : RemoteMediator<Int, GifEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GifEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull() ?: return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                state.pages.size * state.config.pageSize
            }
        }

        try {
            val response = if (query.isBlank()) {
                apiService.getTrendingGifs(offset = page, limit = state.config.pageSize)
            } else {
                apiService.getGifBySearch(
                    q = query,
                    offset = page,
                    limit =  state.config.pageSize
                )
            }

            val gifs = response.data
            val endOfPaginationReached = gifs.isEmpty()

            val gifEntities = gifs.map { gif ->
                val gifUrl = gif.images?.original?.url ?: ""
                val imagePath = saveGifToInternalStorage(gif.id ?: "", gifUrl)
                GifEntity(
                    id = gif.id ?: "",
                    title = gif.title ?: "",
                    gifUrl = gifUrl,
                    imagePath = imagePath,
                    isDeleted = false
                )
            }

            database.withTransaction {
                if (loadType == LoadType.REFRESH && page == 0) {
                    database.gifDao().clearAll()
                }
                database.gifDao().insertAll(gifEntities)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun saveGifToInternalStorage(gifId: String, gifUrl: String): String {

        val gifDirectory = File(context.filesDir, "gifs")
        val file = File(gifDirectory, "$gifId.gif")

        if (!gifDirectory.exists()) {
            val created = gifDirectory.mkdirs()
        }

        if (file.exists()) {
            return file.absolutePath
        }

        try {
            val responseBody = apiService.downloadGif(gifUrl)
            file.outputStream().use { output ->
                responseBody.byteStream().copyTo(output)
            }

            return file.absolutePath
        } catch (e: Exception) {
            throw e
        }
    }
}
