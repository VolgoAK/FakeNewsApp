package com.volgoak.fakenewsapp

import android.app.Application
import com.volgoak.fakenewsapp.di.AppComponent
import com.volgoak.fakenewsapp.di.AppModule
import com.volgoak.fakenewsapp.di.DaggerAppComponent
import timber.log.Timber


class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}