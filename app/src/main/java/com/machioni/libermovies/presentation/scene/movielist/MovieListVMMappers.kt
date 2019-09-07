package com.machioni.libermovies.presentation.scene.movielist

import com.machioni.libermovies.domain.model.Movie
import com.machioni.libermovies.domain.model.Page

fun Page<Movie>.toViewModel() = Page(items.map(Movie::toViewModel), totalResults)

fun Movie.toViewModel() = MovieVM(title, year, imdbId, posterUrl, isFavorite)