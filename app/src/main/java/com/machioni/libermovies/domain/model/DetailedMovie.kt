package com.machioni.libermovies.domain.model

data class DetailedMovie(
        val title: String,
        val year: String,
        val imdbId: Float,
        val posterUrl: String,
        val plot: String,
        val country: String,
        val duration: String
)