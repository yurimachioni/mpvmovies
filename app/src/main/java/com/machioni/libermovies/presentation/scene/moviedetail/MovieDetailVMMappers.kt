package com.machioni.libermovies.presentation.scene.moviedetail

import com.machioni.libermovies.domain.model.DetailedMovie

fun DetailedMovie.toViewModel() = DetailedMovieVM(title, year, imdbId, posterUrl, plot, country,duration, imdbRating)