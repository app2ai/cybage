package com.example.moviesearchapplication.service

import com.example.moviesearchapplication.model.MoviesDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/3/search/movie")
    suspend fun searchMoviesApi(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<MoviesDataModel>
}
