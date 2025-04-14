package com.example.myapplication.state

import com.example.myapplication.model.MovieShort

data class FavoritesState (
    val items: List<MovieShort> = emptyList()
)