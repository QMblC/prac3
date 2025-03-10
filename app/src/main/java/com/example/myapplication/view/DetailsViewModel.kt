package com.example.myapplication.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.myapplication.data.MovieRepository
import com.example.myapplication.model.Movie

class DetailsViewModel(
    private val repository: MovieRepository,
    private val navigation: NavHostController,
    private val id: Int
) : ViewModel() {

    val mutableState = MutableDetailsState()

    init {
        mutableState.movie = repository.getMovie(id)
    }

    fun back() {
        navigation.popBackStack()
    }

    class MutableDetailsState {
        var movie: Movie? by mutableStateOf(null)
    }
}