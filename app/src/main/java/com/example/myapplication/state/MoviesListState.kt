package com.example.myapplication.state

import com.example.myapplication.model.MovieShort
import com.example.myapplication.model.MovieType


interface MoviesListState {
    val items: List<MovieShort>
    val query: String
    val isEmpty: Boolean
    val isLoading: Boolean
    val hasBadge: Boolean
    val showTypesDialog: Boolean
    val typesVariants: Set<MovieType>
    val selectedTypes: Set<MovieType>
    val error: String?
}