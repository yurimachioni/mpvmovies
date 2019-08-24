package com.machioni.libermovies.presentation.scene.moviedetail

data class DetailedMovieVM(
        val title: String,
        val year: String,
        val imdbId: String,
        val posterUrl: String,
        val plot: String,
        val country: String,
        val duration: String,
        val imdbRating: String
)