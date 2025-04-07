package com.example.myapplication.state

import com.example.myapplication.model.Movie
import com.example.myapplication.model.MovieShort


interface MovieDetailState {
    val movie: Movie?
    val rating: Float
    val isRatingVisible: Boolean
    val isLoading: Boolean
    val error: String?
    val related: List<MovieShort>
}