package com.machioni.mpvmovies.presentation.scene.movielist

data class MovieVM(
        val title: String,
        val year: String,
        val imdbId: String,
        val posterUrl: String,
        val isFavorite: Boolean = false
)