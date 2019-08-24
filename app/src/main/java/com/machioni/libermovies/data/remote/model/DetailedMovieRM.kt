package com.machioni.libermovies.data.remote.model

import com.squareup.moshi.Json

data class DetailedMovieRM(
        @Json(name = "Title")
        val title: String,

        @Json(name = "Year")
        val year: String,

        @Json(name = "imdbID")
        val imdbId: Float,

        @Json(name = "Poster")
        val posterUrl: String,

        @Json(name = "Plot")
        val plot: String,

        @Json(name = "Country")
        val country: String,

        @Json(name = "Runtime")
        val duration: String
)