package com.chnoou.movieapp.backend.data

import com.chnoou.movieapp.backend.MovieAPI

data class Movie(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
) {
    companion object {
        const val ID_KEY = "MovieIdKey"
    }

    fun getRatingPercentage(): Float = (vote_average / 2).toFloat()

    fun getImageUrl(): String = "${MovieAPI.BASE_MOVIE_IMAGE_URL}$poster_path"
}
