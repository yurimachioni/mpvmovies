package com.machioni.mpvmovies.domain.model

data class DetailedMovie(
        val title: String,
        val year: String,
        val imdbId: String,
        val posterUrl: String,
        val plot: String,
        val country: String,
        val duration: String,
        val imdbRating: String,
        val isFavorite: Boolean = false
)