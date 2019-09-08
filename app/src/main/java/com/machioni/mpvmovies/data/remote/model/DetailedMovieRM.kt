package com.machioni.mpvmovies.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DetailedMovieRM(
        @Json(name = "Title")
        val title: String,

        @Json(name = "Year")
        val year: String,

        @Json(name = "imdbID")
        val imdbId: String,

        @Json(name = "Poster")
        val posterUrl: String,

        @Json(name = "Plot")
        val plot: String,

        @Json(name = "Country")
        val country: String,

        @Json(name = "Runtime")
        val duration: String,

        @Json(name = "imdbRating")
        val imdbRating: String
)