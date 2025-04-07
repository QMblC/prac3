package com.example.myapplication.api

import com.example.myapplication.model.Movie
import com.example.myapplication.model.MovieFullResponse
import com.example.myapplication.model.MovieShort
import com.example.myapplication.model.MovieShortResponse
import com.example.myapplication.model.MoviesSearchResponse
import com.example.myapplication.model.Person
import com.example.myapplication.model.Rating

class MovieMapper {
    fun toDomain(response: MovieFullResponse): Movie? {
        if (response.id == null || response.name == null) return null

        return Movie(
            id = response.id.or(0),
            name = response.name,
            rating = response.rating ?: Rating(0.0, 0.0, 0.0),
            plot = response.description.orEmpty(),
            premierYear = response.premiere?.world ?: "",
            posterImageURL = response.poster?.url ?: "",
            genres = response.genres?.map {it.name} ?: emptyList(),
            countries = response.countries?.map { it.name } ?: emptyList(),
            people = response.persons
                ?.take(5)
                ?.map {
                    Person(
                        name = it.name ?: "",
                        profession = it.profession,
                        photo = it.photo ?: ""
                    )
                } ?: emptyList()

        )
    }

    fun toDomain(response: MovieShortResponse): MovieShort {
        return MovieShort(
            id = response.id ?: 0,
            name = response.name.orEmpty(),
            posterImageURL = response.poster.url ?: ""
        )
    }

    fun toDomainList(response: MoviesSearchResponse) =
        response.search?.map { movie -> toDomain(movie) }.orEmpty()
}