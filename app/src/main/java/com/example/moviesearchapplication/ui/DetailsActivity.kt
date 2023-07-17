package com.example.moviesearchapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moviesearchapplication.R
import com.example.moviesearchapplication.databinding.ActivityDetailsBinding
import com.example.moviesearchapplication.databinding.ActivityMainBinding
import com.example.moviesearchapplication.model.MovieResult
import com.example.moviesearchapplication.utils.Constants
import com.squareup.picasso.Picasso

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mObj = intent.extras?.getParcelable<MovieResult>("MOVIE")
        binding.movieDesc.text = mObj?.overview
        binding.movieReleaseDate.text = "Release Date: ${mObj?.releaseDate}"
        binding.movieTitle.text = mObj?.title

        val path = "${Constants.POSTER_IMAGE_URL}${mObj?.backdropPath}"

        Picasso.get()
            .load(path)
            .fit()
            .placeholder(R.drawable.baseline_downloading)
            .into(binding.imageView)
    }
}