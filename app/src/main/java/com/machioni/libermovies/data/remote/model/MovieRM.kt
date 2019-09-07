package com.machioni.libermovies.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieRM(
        @Json(name = "Title")
        val title: String,

        @Json(name = "Year")
        val year: String,

        @Json(name = "imdbID")
        val imdbId: String,

        @Json(name = "Poster")
        val posterUrl: String
)