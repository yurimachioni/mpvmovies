package com.machioni.libermovies.data.remote.model

import com.google.gson.annotations.SerializedName

data class MovieRM(
        @SerializedName("Title")
        val title: String,

        @SerializedName("Year")
        val year: String,

        @SerializedName("imdbID")
        val imdbId: String,

        @SerializedName("Poster")
        val posterUrl: String
)