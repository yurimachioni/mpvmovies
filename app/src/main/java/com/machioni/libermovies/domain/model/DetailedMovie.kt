package com.machioni.libermovies.domain.model

data class DetailedMovie(
        val title: String,
        val year: String,
        val imdbId: String,
        val posterUrl: String,
        val plot: String,
        val country: String,
        val duration: String,
        val imdbRating: String
)