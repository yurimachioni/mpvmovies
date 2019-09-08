package com.machioni.mpvmovies.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieSearchRM(
        @Json(name = "Search")
        val moviesPage: List<MovieRM>?,

        @Json(name = "totalResults")
        val totalResults: String?
)