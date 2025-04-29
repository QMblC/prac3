package com.example.myapplication.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.data.Dao.MovieDao
import com.example.myapplication.data.Entity.MovieDatabaseEntity

@Database(entities = [MovieDatabaseEntity::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}