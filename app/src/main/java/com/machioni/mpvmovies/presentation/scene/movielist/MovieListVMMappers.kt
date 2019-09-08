package com.machioni.mpvmovies.presentation.scene.movielist

import com.machioni.mpvmovies.domain.model.Movie
import com.machioni.mpvmovies.domain.model.Page

fun Page<Movie>.toViewModel() = Page(items.map(Movie::toViewModel), totalResults)

fun Movie.toViewModel() = MovieVM(title, year, imdbId, posterUrl, isFavorite)