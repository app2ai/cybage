package com.example.moviesearchapplication.di

import android.content.Context
import com.example.moviesearchapplication.ui.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

// App component dependency provider
@Singleton
@Component(modules = [NetworkModule::class, ViewModelModule::class])
interface MoviesSearchApplicationComponent {
    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): MoviesSearchApplicationComponent
    }
}