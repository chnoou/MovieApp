package com.chnoou.movieapp.backend

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.VolleyError
import com.android.volley.toolbox.Volley
import com.chnoou.movieapp.backend.data.Movie
import com.chnoou.movieapp.backend.data.MovieDetails
import com.chnoou.movieapp.backend.data.TopRatedResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class MovieRepository(private val context: Context) {

    private val TAG = this::class.java.simpleName

    private val _movies: MutableStateFlow<MutableSet<Movie>> = MutableStateFlow(mutableSetOf())
    val movies: StateFlow<MutableSet<Movie>> = _movies

    private val _fetchingMovies: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val fetchingMovies: StateFlow<Boolean> = _fetchingMovies

    private val requestQueue = Volley.newRequestQueue(context)

    private val gson = Gson()

    fun fetchMovies(page: Int = 1) {
        _fetchingMovies.value = true
        val url = "${MovieAPI.TOP_RATED}?${MovieAPI.LANGUAGE}&page=$page"
        MovieAPI.callWithUrl(
            requestQueue,
            url,
            onSuccess = {
                handleMoviesResponse(it)
            },
            onError = {
                handleError(it)
            }
        )
    }

    suspend fun fetchMovieDetails(id: Int): MovieDetails? {
        _fetchingMovies.value = false
        val url = "${MovieAPI.BASE_MOVIE_URL}$id?${MovieAPI.LANGUAGE}"
        return suspendCancellableCoroutine { continuation ->
            MovieAPI.callWithUrl(
                requestQueue,
                url,
                onSuccess = {
                    val movie = parseMovieDetails(it)
                    continuation.resume(movie)
                },
                onError = {
                    handleError(it)
                    continuation.resume(null)
                }
            )
        }
    }

    private fun handleMoviesResponse(response: String) {
        try {
            val parsedResponse = gson.fromJson(response, TopRatedResponse::class.java)
            if (parsedResponse.results.isNotEmpty()) {
                val newSet = mutableSetOf<Movie>()
                newSet.addAll(_movies.value)
                newSet.addAll(parsedResponse.results)
                _movies.value = newSet
                Log.d(TAG, "Added ${parsedResponse.results.size} movies")
                Log.d(TAG, "Movies are now ${_movies.value.map { it.title }}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to parse result due to: ${e.message}")
        }
    }

    private fun parseMovieDetails(json: String): MovieDetails? {
        return try {
            val movie = gson.fromJson(json, MovieDetails::class.java)
            movie
        } catch (e: Exception) {
            Log.e(TAG, "Failed to parse movie details due to: ${e.message}")
            null
        }
    }



    private fun handleError(error: VolleyError) {
        _fetchingMovies.value = false
        Toast.makeText(context, "Something went wrong: ${error.message}", Toast.LENGTH_LONG).show()
    }

    fun getMovie(id: Int): Movie? = _movies.value.firstOrNull { it.id == id }

}