package com.san.customexoplayer.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteVideoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(video: FavoriteVideo)

    @Query("SELECT * FROM favorites")
    fun getFavorites(): Flow<List<FavoriteVideo>>

    @Delete
    suspend fun removeFavorite(video: FavoriteVideo)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE videoUrl = :url)")
    suspend fun isFavorite(url: String): Boolean
}
