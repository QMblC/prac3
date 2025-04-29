package com.example.myapplication.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.data.Dao.ProfileDao
import com.example.myapplication.data.Entity.ProfileDatabaseEntity

@Database(entities = [ProfileDatabaseEntity::class], version = 1)
abstract class ProfileDatabase : RoomDatabase(){
    abstract fun profileDao(): ProfileDao
}