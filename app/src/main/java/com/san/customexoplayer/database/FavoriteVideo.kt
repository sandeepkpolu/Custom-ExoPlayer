package com.san.customexoplayer.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteVideo(
    @PrimaryKey
    val videoUrl: String,
    val title: String,
)