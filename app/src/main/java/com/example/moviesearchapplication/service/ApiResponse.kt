package com.example.moviesearchapplication.service

import com.example.moviesearchapplication.model.MoviesDataModel

/*
Collect API response dynamically for all type of request
T is generic class for response
*/
sealed class ApiResponse {
    data class Success(val data: MoviesDataModel) : ApiResponse()
    object APIException: ApiResponse()
    object ApiUnSuccess: ApiResponse()
}
