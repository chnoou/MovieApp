package com.chnoou.movieapp.backend

import android.content.Context
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.chnoou.movieapp.R
import com.chnoou.movieapp.backend.data.Movie
import com.chnoou.movieapp.backend.data.TopRatedResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.koinApplication
import kotlin.coroutines.coroutineContext

const val AUTHORIZATION_HEADER = "Authorization"
const val ACCEPT_HEADER = "accept"
const val APPLICATION_JSON = "application/json"
const val API_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5ZjUzM2YwOGI2NGNhMTc4YjM2OWM2NjY3Mzg5YTZjZCIsInN1YiI6IjY1NDExMTE4NTc1MzBlMDEyY2Y0YzYyNSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.fUvZKcoQ9whkL4eafl12VPSAzORFjO-yFDI-ew4DWx4"

class MovieRepository(context: Context) {

    private val TAG = this::class.java.simpleName

    private val _movies: MutableStateFlow<MutableSet<Movie>> = MutableStateFlow(mutableSetOf())
    val movies: StateFlow<MutableSet<Movie>> = _movies

    private val requestQueue = Volley.newRequestQueue(context)

    private val gson = Gson()

    fun fetchMovies(page: Int = 1) {
        val request = object: StringRequest(
            Method.GET,
            "https://api.themoviedb.org/3/movie/top_rated?language=en-US&page=$page",
            Response.Listener {
                handleResponse(it)
            },
            Response.ErrorListener {
                handleError(it)
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers[AUTHORIZATION_HEADER] = "Bearer $API_TOKEN"
                headers[ACCEPT_HEADER] = APPLICATION_JSON
                Log.d(TAG, "headers: $headers")
                return headers
            }
        }

        request.retryPolicy =
            DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )

        requestQueue.add(request)
    }

    private fun handleResponse(response: String) {
        try {
            val parsedResponse = gson.fromJson(response, TopRatedResponse::class.java)
            if (parsedResponse.results.isNotEmpty()) {
                _movies.value.addAll(parsedResponse.results)
                Log.d(TAG, "Added ${parsedResponse.results.size} movies")
                Log.d(TAG, "Movies are now ${_movies.value.map { it.title }}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to parse result due to: ${e.message}")
        }
    }

    private fun handleError(error: VolleyError) {

    }

}