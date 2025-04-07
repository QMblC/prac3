package com.example.myapplication.model

class Movie (
    val id: Int = 0,
    val name: String = "",
    val rating: Rating = Rating(0.0,0.0,0.0),
    val plot: String = "",
    var premierYear: String = "",
    val posterImageURL: String = "",
    val genres: List<String> = listOf(),
    val countries: List<String> = listOf(),
    val people: List<Person> = listOf()
)

