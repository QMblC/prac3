package com.example.myapplication.di

import android.content.Context
import androidx.room.Room
import com.example.myapplication.data.MovieDatabase
import org.koin.dsl.module

val dbModule = module {
    single { DatabaseBuilder.getInstance(get()) }
}

object DatabaseBuilder {
    private var INSTANCE: MovieDatabase? = null

    fun getInstance(context: Context): MovieDatabase {
        if (INSTANCE == null) {
            synchronized(MovieDatabase::class) {
                INSTANCE = buildRoomDB(context)
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            MovieDatabase::class.java,
            "movies"
        ).build()
}