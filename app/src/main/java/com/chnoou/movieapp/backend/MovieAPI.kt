package com.chnoou.movieapp.backend

import com.android.volley.DefaultRetryPolicy
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest

object MovieAPI {

    private const val ACCEPT_HEADER = "accept"
    private const val APPLICATION_JSON = "application/json"
    private const val AUTHORIZATION_HEADER = "Authorization"
    private const val API_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5ZjUzM2YwOGI2NGNhMTc4YjM2OWM2NjY3Mzg5YTZjZCIsInN1YiI6IjY1NDExMTE4NTc1MzBlMDEyY2Y0YzYyNSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.fUvZKcoQ9whkL4eafl12VPSAzORFjO-yFDI-ew4DWx4"

    // Exposed
    const val BASE_MOVIE_IMAGE_URL = "https://image.tmdb.org/t/p/original"
    const val BASE_MOVIE_URL = "https://api.themoviedb.org/3/movie/"
    const val TOP_RATED = "top_rated"
    const val POPULAR = "popular"
    const val LANGUAGE = "language=en-US"

    fun callWithUrl(queue: RequestQueue,url: String, onSuccess: (String) -> Unit, onError: (VolleyError) -> Unit) {
        val request = object: StringRequest(
            Method.GET,
            url,
            Response.Listener {
                onSuccess(it)
            },
            Response.ErrorListener {
                onError(it)
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers[AUTHORIZATION_HEADER] = "Bearer $API_TOKEN"
                headers[ACCEPT_HEADER] = APPLICATION_JSON
                return headers
            }
        }

        request.retryPolicy =
            DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )

        queue.add(request)
    }

}