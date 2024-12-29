package com.san.customexoplayer.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteVideo::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteVideoDao(): FavoriteVideoDao
}
