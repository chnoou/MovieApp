package com.chnoou.movieapp.ui.home_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.chnoou.movieapp.backend.MovieRepository

class HomeViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    val movies = movieRepository.movies.asLiveData()

    fun fetchMovies() {
        movieRepository.fetchMovies()
    }




}