package com.example.myapplication.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

interface ProfileState {
    var fullName: String
    var avatarUri: String
    var resumeUrl: String
    var position: String
    var email: String
    var favouriteLessonTime: String
    var isLoading: Boolean
    var error: String?
}

class MutableProfileState : ProfileState {
    override var fullName: String by mutableStateOf("")
    override var avatarUri: String by mutableStateOf("")
    override var resumeUrl: String by mutableStateOf("")
    override var position: String by mutableStateOf("")
    override var email: String by mutableStateOf("")
    override var favouriteLessonTime: String by mutableStateOf("")
    override var isLoading: Boolean by mutableStateOf(false)
    override var error: String? by mutableStateOf(null)
}