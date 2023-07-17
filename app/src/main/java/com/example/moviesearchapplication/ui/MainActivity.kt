package com.example.moviesearchapplication.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearchapplication.MoviesSearchApplication
import com.example.moviesearchapplication.databinding.ActivityMainBinding
import com.example.moviesearchapplication.di.ViewModelFactory
import com.example.moviesearchapplication.model.MovieResult
import com.example.moviesearchapplication.vm.Failed
import com.example.moviesearchapplication.vm.InProgress
import com.example.moviesearchapplication.vm.MainViewModel
import com.example.moviesearchapplication.vm.Success
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MovieItemListener {
    private lateinit var binding: ActivityMainBinding
    private var currentPageNumber = 1

    private val mAdapter: MovieAdapter by lazy {
        MovieAdapter(this)
    }
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        viewModelFactory.create(
            MainViewModel::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MoviesSearchApplication).appComponent.inject(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding.movieRecycler){
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            adapter = mAdapter
            setHasFixedSize(true)
        }
        binding.searchBox.searchIcon.setOnClickListener {
            // Hide virtual keyboard
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.root.windowToken, 0)

            viewModel.callMoviesRemotely(binding.searchBox.etSearchBox.text.toString())
        }
        observeData()
    }

    private fun observeData() {
        viewModel.movies.observe(this) {
            it?.let {
                when (it) {
                    Failed -> {
                        Toast.makeText(this, "API failed to load data", Toast.LENGTH_SHORT).show()
                        switchProgressBar(false)
                    }
                    InProgress -> {
                        switchProgressBar(true)
                    }
                    is Success -> {
                        switchProgressBar(false)
                        binding.movieRecycler.visibility = View.VISIBLE
                        binding.txtSearchResult.visibility = View.VISIBLE
                        mAdapter.appendList(it.data.results)//differ.submitList(it.data.results)
                        if (it.data.results.isEmpty()){
                            Toast.makeText(this, "No result found", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun switchProgressBar(isActive: Boolean) {
        if (isActive) {
            binding.progressCircular.visibility = View.VISIBLE
        } else {
            binding.progressCircular.visibility = View.GONE
        }
    }

    override fun clickToGoDetailPage(mId: MovieResult) {
        // Call details activity
        val i = Intent(this, DetailsActivity::class.java)
        i.putExtra("MOVIE", mId)
        startActivity(i)
    }

    override fun loadMoreDataToRecyclerView() {
        viewModel.callMoviesRemotely(binding.searchBox.etSearchBox.text.toString(), ++currentPageNumber)
    }
}