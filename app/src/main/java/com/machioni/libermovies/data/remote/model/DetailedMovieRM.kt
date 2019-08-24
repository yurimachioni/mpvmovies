package com.machioni.libermovies.data.remote.model

import com.google.gson.annotations.SerializedName

data class DetailedMovieRM(
        @SerializedName("Title")
        val title: String,

        @SerializedName("Year")
        val year: String,

        @SerializedName("imdbID")
        val imdbId: String,

        @SerializedName("Poster")
        val posterUrl: String,

        @SerializedName("Plot")
        val plot: String,

        @SerializedName("Country")
        val country: String,

        @SerializedName("Runtime")
        val duration: String,

        @SerializedName("imdbRating")
        val imdbRating: String
)