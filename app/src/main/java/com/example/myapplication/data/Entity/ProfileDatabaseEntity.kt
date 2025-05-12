package com.example.myapplication.data.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ProfileDatabaseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "profileFullName") val fullName: String?,
    @ColumnInfo(name = "profileAvatar") val avatarUri: String?,
    @ColumnInfo(name = "profileResume") val resumeUrl: String?,
    @ColumnInfo(name = "profilePosition") val position: String?,
    @ColumnInfo(name = "profileEmail") val email: String?,
    @ColumnInfo(name = "profileFavouriteLessonTime") val favouriteLessonTime: String?
)