package com.example.myapplication.data

import com.example.myapplication.data.Entity.ProfileDatabaseEntity
import com.example.myapplication.model.Profile
import kotlinx.coroutines.flow.Flow

class ProfileRepository(
    private val db: ProfileDatabase
) {
    suspend fun getProfile(): Profile? {
        return db.profileDao().getUser()?.toDomain()
    }

    suspend fun setProfile(profile: Profile): Profile {
        val existingProfile = db.profileDao().getUser()
        if (existingProfile == null) {
            val profileEntity = ProfileDatabaseEntity(
                fullName = profile.fullName,
                avatarUri = profile.avatarUri,
                resumeUrl = profile.resumeUrl,
                position = profile.position,
                email = profile.email
            )
            db.profileDao().insert(profileEntity)
        } else {
            db.profileDao().update(
                fullName = profile.fullName,
                avatarUri = profile.avatarUri,
                resumeUrl = profile.resumeUrl,
                position = profile.position,
                email = profile.email
            )
        }
        return profile
    }

    suspend fun observeProfile(): Flow<Profile> {
        return db.profileDao().getUser()?.toDomain()?.let { profile ->
            kotlinx.coroutines.flow.flowOf(profile)
        } ?: kotlinx.coroutines.flow.flowOf(Profile())
    }

    fun ProfileDatabaseEntity.toDomain(): Profile {
        return Profile(
            fullName = fullName ?: "",
            avatarUri = avatarUri ?: "",
            resumeUrl = resumeUrl ?: "",
            position = position ?: "",
            email = email ?: ""
        )
    }
}