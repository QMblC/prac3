package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieDao {
    @Query("SELECT * FROM MovieDatabaseEntity")
    suspend fun getAll(): List<MovieDatabaseEntity>

    @Insert
    suspend fun insert(driverDbEntity: MovieDatabaseEntity)
}