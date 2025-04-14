package com.example.myapplication.model

import com.example.myapplication.model.Dto.GenreDto
import com.example.myapplication.model.MovieAttributes.Poster
import com.google.gson.annotations.SerializedName

class MoviesSearchResponse(
    @SerializedName("docs")
    val search: List<MovieShortResponse>?
)

class MovieShortResponse(
    val id: Int?,
    val name: String?,
    val poster: Poster,
    val type: String,
    val genres: List<GenreDto>
)