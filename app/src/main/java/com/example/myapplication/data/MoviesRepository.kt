package com.example.myapplication.data

import android.util.Log
import com.example.myapplication.api.MovieApi
import com.example.myapplication.api.MovieMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepository(
    private val api: MovieApi,
    private val mapper: MovieMapper
) {
    suspend fun getList(query: String) =
        withContext(Dispatchers.IO) {
            val response = api.searchMovies(query = query)
            mapper.toDomainList(response)
        }

    suspend fun getById(id: Int) =
        withContext(Dispatchers.IO) {
            val response = api.getMovie(id)
            mapper.toDomain(response)
        }
}