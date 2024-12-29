package com.san.customexoplayer.di

import com.san.customexoplayer.ui.viewmodels.VideoPlayerViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(videoPlayerViewModel: VideoPlayerViewModel)

}