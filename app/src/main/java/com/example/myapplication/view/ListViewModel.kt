package com.example.myapplication.view

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.myapplication.data.MovieRepository
import com.example.myapplication.model.Movie

class ListViewModel(
    private val repository: MovieRepository,
    private val navigation: NavHostController
) : ViewModel() {

    init {
        loadMovies()
    }

    fun loadMovies(): List<Movie> {
        return repository.getList()
    }

    fun onItemClicked(id: Int) {
        navigation.navigate("filmDetail/$id")
    }
}