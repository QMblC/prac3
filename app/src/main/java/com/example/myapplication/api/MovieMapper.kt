package com.example.myapplication.api

import com.example.myapplication.model.Genre
import com.example.myapplication.model.Movie
import com.example.myapplication.model.MovieFullResponse
import com.example.myapplication.model.MovieShort
import com.example.myapplication.model.MovieShortResponse
import com.example.myapplication.model.MoviesSearchResponse
import com.example.myapplication.model.MovieAttributes.Person
import com.example.myapplication.model.MovieAttributes.Rating
import com.example.myapplication.model.MovieType

class MovieMapper {
    fun toDomain(response: MovieFullResponse): Movie? {
        if (response.id == null
            || response.name.isNullOrBlank()
        ) return null

        return Movie(
            id = response.id.or(0),
            name = response.name,
            rating = response.rating ?: Rating(0.0, 0.0, 0.0),
            plot = response.description.orEmpty(),
            premierYear = response.premiere?.world ?: "",
            posterImageURL = response.poster?.url ?: "",
            genres = response.genres?.map { genre -> Genre.getGenreByName(genre.name) }
                ?: emptyList(),
            countries = response.countries?.map { country -> country.name } ?: emptyList(),
            type = MovieType.getMovieTypeByCode(response.type),

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
            type = response.type.orEmpty(),
            genres = response.genres?.map { genre -> Genre.getGenreByName(genre.name) }
                ?: emptyList(),
            posterImageURL = response.poster.url ?: ""
        )
    }

    fun toDomainList(response: List<MovieShortResponse>) =
        response.filter { movie -> movie.id != null && !movie.name.isNullOrBlank() }
            .map { movie ->
                toDomain(movie)
            }
}