package com.example.myapplication.model

import androidx.annotation.Keep
import com.example.myapplication.model.Dto.CountryDto
import com.example.myapplication.model.Dto.GenreDto
import com.example.myapplication.model.Dto.PremiereDto
import com.example.myapplication.model.MovieAttributes.Person
import com.example.myapplication.model.MovieAttributes.Poster
import com.example.myapplication.model.MovieAttributes.Rating
import com.example.myapplication.model.MovieAttributes.Votes

@Keep
class MovieFullResponse(
    val id: Int?,
    val name: String?,
    val type: String?,
    val rating: Rating?,
    val description: String?,
    val votes: Votes?,
    val premiere: PremiereDto?,
    val poster: Poster?,
    val genres: List<GenreDto>?,
    val countries: List<CountryDto>?,
    val persons: List<Person>?
)