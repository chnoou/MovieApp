package com.chnoou.movieapp.ui.detailed_movie_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chnoou.movieapp.backend.MovieRepository
import com.chnoou.movieapp.backend.data.Movie
import com.chnoou.movieapp.backend.data.MovieDetails
import kotlinx.coroutines.launch

class DetailedMovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val _movie: MutableLiveData<Movie?> = MutableLiveData(null)
    val movie: LiveData<Movie?> = _movie

    private val _movieDetails: MutableLiveData<MovieDetails?> = MutableLiveData(null)
    val movieDetails: LiveData<MovieDetails?> = _movieDetails

    fun getMovieFromId(id: Int) {
        _movie.value = movieRepository.getMovie(id)
        viewModelScope.launch {
            _movieDetails.value = movieRepository.fetchMovieDetails(id)
            // Might want to check if returned movie is null and show an error message?
        }
    }



}