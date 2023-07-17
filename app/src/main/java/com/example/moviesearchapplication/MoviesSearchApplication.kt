package com.example.moviesearchapplication

import android.app.Application
import com.example.moviesearchapplication.di.MoviesSearchApplicationComponent
import com.example.moviesearchapplication.di.DaggerMoviesSearchApplicationComponent


class MoviesSearchApplication : Application() {
    lateinit var appComponent: MoviesSearchApplicationComponent

    override fun onCreate() {
        super.onCreate()
        // Initialise app component here
        appComponent = DaggerMoviesSearchApplicationComponent.factory().create(applicationContext)
    }
}