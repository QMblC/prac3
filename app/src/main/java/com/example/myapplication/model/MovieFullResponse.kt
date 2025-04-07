package com.example.myapplication.model

import androidx.annotation.Keep

@Keep
class MovieFullResponse(
    val id: Int?,
    val name: String?,
    val rating: Rating?,
    val description: String?,
    val votes: Votes?,
    val premiere: Premiere?,
    val poster: Poster?,
    val genres: List<Genre>?,
    val countries: List<Country>?,
    val persons: List<Person>?
)

class Poster(
    val url: String,
    val previewUrl: String
) {
}

class Votes(
    val kp: Int,
    val imdb: Int,
    val filmCritics: Int,
) {
}

class Premiere(
    val world: String
)

class Genre(
    val name: String
)

class Country(
    val name: String
)