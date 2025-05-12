package com.example.myapplication.api


import com.example.myapplication.model.MovieFullResponse
import com.example.myapplication.model.MoviesSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/search")
    suspend fun searchMovies(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query("query") query: String,
    ): MoviesSearchResponse

    @GET("movie/{id}")
    suspend fun getMovie(
        @Path("id") id: Int? = null,
    ): MovieFullResponse
}