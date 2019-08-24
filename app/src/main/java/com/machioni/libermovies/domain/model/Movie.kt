package com.machioni.libermovies.domain.model

data class Movie(
        val title: String,
        val year: String,
        val imdbId: String,
        val posterUrl: String,
        val isFavorite: Boolean = false
)