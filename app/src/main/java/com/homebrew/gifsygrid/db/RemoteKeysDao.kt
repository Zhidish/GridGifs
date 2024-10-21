package com.homebrew.gifsygrid.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeysDao {

    @Query("SELECT * FROM remote_keys LIMIT 1")
    suspend fun getRemoteKeys(): RemoteKeys?

    @Query("SELECT * FROM remote_keys WHERE gifId = :gifId")
    suspend fun remoteKeysGifId(gifId: String): RemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<RemoteKeys>)

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}