package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

class MoviesSearchResponse(
    @SerializedName("docs")
    val search: List<MovieShortResponse>?
)

class MovieShortResponse(
    val id: Int?,
    val name: String?,
    val poster: Poster,
)