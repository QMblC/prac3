package com.example.myapplication.model

class MovieShort(
    val id: Int,
    val name: String,
    val type: String,
    val genres: List<Genre>,
    val posterImageURL: String,
)