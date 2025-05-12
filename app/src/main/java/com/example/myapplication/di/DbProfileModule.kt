package com.example.myapplication.di

import android.content.Context
import androidx.room.Room
import com.example.myapplication.data.ProfileDatabase
import org.koin.dsl.module

val dbProfileModule = module {
    single { DatabaseProfileBuilder.getInstance(get()) }
}

object DatabaseProfileBuilder {
    private var instance: ProfileDatabase? = null

    fun getInstance(context: Context): ProfileDatabase {
        if (instance == null) {
            synchronized(ProfileDatabase::class) {
                instance = buildRoomDB(context)
            }
        }
        return instance!!
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            ProfileDatabase::class.java,
            "profiles"
        ).build()
}