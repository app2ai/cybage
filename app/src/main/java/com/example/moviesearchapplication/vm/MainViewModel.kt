package com.example.moviesearchapplication.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesearchapplication.model.MoviesDataModel
import com.example.moviesearchapplication.repo.MoviesRemoteApiRepository
import com.example.moviesearchapplication.service.ApiResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: MoviesRemoteApiRepository
): ViewModel() {
    private var _movies = MutableLiveData<MoviesData>()
    val movies: LiveData<MoviesData> = _movies

    fun callMoviesRemotely(query: String, pageNumber: Int = 1) {
        viewModelScope.launch {
            _movies.value = InProgress
            when(val res = repository.apiInvoked(query, pageNumber)) {
                ApiResponse.ApiUnSuccess -> {
                    _movies.value = Failed
                }
                ApiResponse.APIException -> {
                    _movies.value = Failed
                }
                is ApiResponse.Success -> {
                    _movies.value = Success(res.data)
                }
            }
        }
    }
}

// API Response sealed status
sealed class MoviesData
data class Success(val data: MoviesDataModel) : MoviesData()
object Failed : MoviesData()
object InProgress : MoviesData()