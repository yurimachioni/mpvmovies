package com.machioni.libermovies.presentation.scene.movielist

import com.machioni.libermovies.domain.model.Movie

fun Movie.toViewModel() = MovieVM(title, year, imdbId, posterUrl, isFavorite)