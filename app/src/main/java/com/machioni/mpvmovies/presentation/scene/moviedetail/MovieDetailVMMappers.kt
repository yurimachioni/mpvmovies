package com.machioni.mpvmovies.presentation.scene.moviedetail

import com.machioni.mpvmovies.domain.model.DetailedMovie

fun DetailedMovie.toViewModel() = DetailedMovieVM(title, year, imdbId, posterUrl, plot, country,duration, imdbRating)