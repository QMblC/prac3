package com.example.myapplication.view

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.myapplication.data.MovieRepository
import com.example.myapplication.model.Movie

class ListViewModel(
    private val repository: MovieRepository,
    private val navigation: NavHostController,
    private var movies: List<Movie> = emptyList()
) : ViewModel() {

    init {
        movies = loadMovies()
    }

    fun loadMovies(): List<Movie> {
        if (movies.isEmpty())
            return repository.getList()
        else
            return movies
    }

    fun onItemClicked(id: Int) {
        navigation.navigate("filmDetail/$id")
    }
}