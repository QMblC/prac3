package com.example.myapplication.data

import android.util.Log
import com.example.myapplication.api.MovieApi
import com.example.myapplication.api.MovieMapper
import com.example.myapplication.model.Genre
import com.example.myapplication.model.MovieAttributes.Poster
import com.example.myapplication.model.MovieShort
import com.example.myapplication.model.MovieType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepository(
    private val api: MovieApi,
    private val mapper: MovieMapper,
    private val db: MovieDatabase
) {

    suspend fun getList(query: String, filterTypes: Set<MovieType>? = null) =
        withContext(Dispatchers.IO) {
            val response = api.searchMovies(query = query).search
                .orEmpty()
                .filter { movie ->
                    filterTypes.isNullOrEmpty()
                            || filterTypes.contains(MovieType.getMovieTypeByCode(movie.type))
                }
            mapper.toDomainList(response)
        }

    suspend fun getById(id: Int) =
        withContext(Dispatchers.IO) {
            val response = api.getMovie(id)
            mapper.toDomain(response)
        }

    suspend fun saveFavorite(movie: MovieShort) =
        withContext(Dispatchers.IO) {
            db.movieDao().insert(
                MovieDatabaseEntity(
                    name = movie.name,
                    type = movie.type,
                    genre = movie.genres.getOrNull(0)?.name ?: "",
                    url = movie.posterImageURL
                )
            )
        }

    suspend fun getFavorites() =
        withContext(Dispatchers.IO) {
            db.movieDao().getAll().map {
                MovieShort(
                    it.id ?: 0,
                    it.name.orEmpty(),
                    it.type.orEmpty(),
                    listOf(Genre.valueOf(it.genre.toString())),
                    it.url.orEmpty()
                )
            }
        }
}