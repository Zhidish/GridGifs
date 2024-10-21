package com.homebrew.gifsygrid.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gifs")
data class GifEntity(
    @PrimaryKey val id: String,
    val title: String,
    val gifUrl: String,
    val imagePath: String,
    val isDeleted: Boolean
)