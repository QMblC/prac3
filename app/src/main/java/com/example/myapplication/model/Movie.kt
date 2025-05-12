package com.example.myapplication.model

import com.example.myapplication.model.MovieAttributes.Person
import com.example.myapplication.model.MovieAttributes.Rating

class Movie (
    val id: Int,
    val name: String,
    val type: MovieType,
    val rating: Rating = Rating(0.0,0.0,0.0),
    val plot: String = "",
    var premierYear: String = "",
    val posterImageURL: String = "",
    val genres: List<Genre> = listOf(),
    val countries: List<String> = listOf(),
    val people: List<Person> = listOf()
)

