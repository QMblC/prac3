package com.example.myapplication.state

import com.example.myapplication.model.MovieShort


interface MoviesListState {
    val items: List<MovieShort>
    val query: String
    val isEmpty: Boolean
    val isLoading: Boolean
    val error: String?
}