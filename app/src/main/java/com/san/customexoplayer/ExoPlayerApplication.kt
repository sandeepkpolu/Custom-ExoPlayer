package com.san.customexoplayer

import android.app.Application
import com.san.customexoplayer.di.AppComponent
import com.san.customexoplayer.di.AppModule
import com.san.customexoplayer.di.DaggerAppComponent

class ExoPlayerApplication: Application() {

    companion object {
        lateinit var application: ExoPlayerApplication
    }

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        application = this
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

}