package com.example.myapplication.data.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.data.Entity.MovieDatabaseEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM MovieDatabaseEntity")
    suspend fun getAll(): List<MovieDatabaseEntity>

    @Insert
    suspend fun insert(driverDbEntity: MovieDatabaseEntity)
}