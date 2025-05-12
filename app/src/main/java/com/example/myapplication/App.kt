package com.example.myapplication

import android.app.Application
import com.example.myapplication.di.dbMovieModule
import com.example.myapplication.di.dbProfileModule
import com.example.myapplication.di.restModule
import com.example.myapplication.di.rootModule
import com.example.myapplication.utilities.Notification
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Notification.createNotificationChannel(this)

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(rootModule, restModule, dbMovieModule, dbProfileModule)
        }
    }
}