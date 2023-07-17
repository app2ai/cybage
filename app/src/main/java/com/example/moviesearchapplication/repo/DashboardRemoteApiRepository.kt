package com.example.moviesearchapplication.repo

import com.example.moviesearchapplication.service.ApiResponse
import com.example.moviesearchapplication.service.ApiService
import javax.inject.Inject

class MoviesRemoteApiRepository @Inject constructor(
    private val service: ApiService
) {
    suspend fun apiInvoked(searchQuery: String, page: Int) : ApiResponse {
        return try {
            service.searchMoviesApi(
                searchQuery, page
            ).body()?.let {
                ApiResponse.Success(it)
            } ?: ApiResponse.ApiUnSuccess
        } catch (ex: Exception) {
            ApiResponse.APIException
        }
    }
}