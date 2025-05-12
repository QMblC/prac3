package com.example.myapplication.data.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.data.Entity.ProfileDatabaseEntity

@Dao
interface ProfileDao {
    @Query("SELECT * FROM ProfileDatabaseEntity LIMIT 1")
    suspend fun getUser(): ProfileDatabaseEntity?

    @Insert
    suspend fun insert(driverDbEntity: ProfileDatabaseEntity)


    @Query("UPDATE ProfileDatabaseEntity SET profileFullName = :fullName, profileAvatar = :avatarUri, profileResume = :resumeUrl, profilePosition = :position, profileEmail = :email, profileFavouriteLessonTime = :favouriteLessonTime")
    suspend fun update(fullName: String?, avatarUri: String?, resumeUrl: String?, position: String?, email: String?, favouriteLessonTime: String?)
}