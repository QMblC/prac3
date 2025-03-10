package com.example.myapplication.model

class Movie (
    val id: Int,
    val name: String,
    val rating: Rating,
    val plot: String,
    var premierYear: Int,
    val posterImageURL: String,
    val genres: List<String>,
    val countries: List<String>,
    val people: List<Person>
)

