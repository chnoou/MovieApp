package com.chnoou.movieapp.backend.data

data class MovieDetails(
    val adult: Boolean,
    val backdrop_path: String,
    val belongs_to_collection: BelongsToCollection,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val imdb_id: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val production_companies: List<ProductionCompany>,
    val production_countries: List<ProductionCountry>,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val spoken_languages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
) {
    fun printInfo(): String = "$release_date   |   ${genres.joinToString(" ") { it.name }}   |   ${printRuntime()}"

    fun printLanguages(): String = spoken_languages.joinToString("\n") { it.english_name }

    fun printProductionCompanies(): String = production_companies.joinToString("\n") { it.name }

    fun printProductionCountries(): String = production_countries.joinToString("\n") { it.name }

    fun printRuntime(): String {
        val hrs = runtime / 60
        val minutes = runtime % 60
        var string = ""
        if (hrs > 0) {
            string += "${hrs}h"
            if (minutes > 0) {
                string += " "
            }
        }
        if (minutes > 0) {
            string += "${minutes}m"
        }
        return string
    }
}