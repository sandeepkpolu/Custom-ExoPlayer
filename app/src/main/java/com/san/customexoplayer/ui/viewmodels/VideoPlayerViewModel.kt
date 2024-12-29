package com.san.customexoplayer.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.san.customexoplayer.ExoPlayerApplication
import com.san.customexoplayer.database.AppDatabase
import com.san.customexoplayer.database.FavoriteVideo
import kotlinx.coroutines.launch
import javax.inject.Inject

class VideoPlayerViewModel : ViewModel() {

    @Inject
    lateinit var appDatabase: AppDatabase

    init {
        ExoPlayerApplication.application.appComponent.inject(this)
    }

    val favoriteVideos = appDatabase.favoriteVideoDao().getFavorites()
        .asLiveData(viewModelScope.coroutineContext)

    fun addFavorite(video: FavoriteVideo) {
        viewModelScope.launch {
            appDatabase.favoriteVideoDao().addFavorite(video)
        }
    }

    fun removeFavorite(video: FavoriteVideo) {
        viewModelScope.launch {
            appDatabase.favoriteVideoDao().removeFavorite(video)
        }
    }

    suspend fun isFavorite(url: String): Boolean {
        return appDatabase.favoriteVideoDao().isFavorite(url)
    }
}
