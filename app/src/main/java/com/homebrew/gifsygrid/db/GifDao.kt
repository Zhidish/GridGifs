package com.homebrew.gifsygrid.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GifDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(gifs: List<GifEntity>)

    @Query("""
        SELECT * FROM gifs 
        WHERE isDeleted = 0 AND title LIKE '%' || :query || '%' 
        ORDER BY id
    """)
    fun getGifs(query: String): PagingSource<Int, GifEntity>

    @Query("UPDATE gifs SET isDeleted = 1 WHERE id = :gifId")
    suspend fun markGifAsDeleted(gifId: String)

    @Query("DELETE FROM gifs WHERE isDeleted = 1")
    suspend fun clearDeletedGifs()

    @Query("DELETE FROM gifs")
    suspend fun clearAll()

    @Query("""
    SELECT * FROM gifs 
    WHERE isDeleted = 0
    ORDER BY id
""")
    fun getAllGifs(): PagingSource<Int, GifEntity>
}