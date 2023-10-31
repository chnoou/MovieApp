package com.chnoou.movieapp.backend.data

data class TopRatedResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)