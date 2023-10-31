package com.chnoou.movieapp.backend

import com.chnoou.movieapp.backend.data.Movie
import kotlinx.coroutines.flow.MutableStateFlow

class MovieRepository {

    private val _movies: MutableStateFlow<ArrayList<Movie>> = MutableStateFlow(arrayListOf())

    fun fetchMovies(page: Int = 1) {

    }

}