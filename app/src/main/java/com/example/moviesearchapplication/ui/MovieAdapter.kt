package com.example.moviesearchapplication.ui

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.moviesearchapplication.R
import com.example.moviesearchapplication.databinding.MovieCardBinding
import com.example.moviesearchapplication.model.MovieResult
import com.example.moviesearchapplication.utils.Constants
import com.squareup.picasso.Picasso

class MovieAdapter(listener: MovieItemListener) : Adapter<MovieAdapter.MovieViewHolder>() {

    private lateinit var binding: MovieCardBinding
    private var mlistener: MovieItemListener = listener

    inner class MovieViewHolder : ViewHolder(binding.root) {
        fun setData(item : MovieResult){
            print("Data: ${item.toString()}")
            binding.apply {
                movieTitle.text = item.title
                movieDesc.text = item.overview
                root.setOnClickListener {
                    mlistener.clickToGoDetailPage(item)
                }
            }
            val path = "${Constants.IMAGE_URL}${item.backdropPath}"
            Log.d("Thumbnail", "Path: $path")
            Picasso.get()
                .load(path)
                .fit()
                .placeholder(R.drawable.baseline_downloading)
                .into(binding.imgThumbnail)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        binding = MovieCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder()
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.setData(movies[position])
        holder.setIsRecyclable(false)
        if (position == movies.size - 1) {
            mlistener.loadMoreDataToRecyclerView()
        }
    }

    // private val differCallback = object : DiffUtil.ItemCallback<MovieResult>() {
    //     override fun areItemsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
    //         return oldItem.id == newItem.id
    //     }
    //
    //     @SuppressLint("DiffUtilEquals")
    //     override fun areContentsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
    //         return oldItem == newItem
    //     }
    // }
    //
    // val differ = AsyncListDiffer(this, differCallback)
    fun appendList(lst: List<MovieResult>?) {
        if (lst.isNullOrEmpty()) {
            return
        } else {
            movies.addAll(lst)
        }
    }

    companion object {
        var movies: MutableList<MovieResult> = mutableListOf()
    }
}

interface MovieItemListener {
    fun clickToGoDetailPage(mId: MovieResult)
    fun loadMoreDataToRecyclerView()
}