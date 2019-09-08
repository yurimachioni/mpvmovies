package com.machioni.mpvmovies.data.mapper

import com.machioni.mpvmovies.data.remote.model.DetailedMovieRM
import com.machioni.mpvmovies.data.remote.model.MovieRM
import com.machioni.mpvmovies.data.remote.model.MovieSearchRM
import com.machioni.mpvmovies.domain.model.DetailedMovie
import com.machioni.mpvmovies.domain.model.Movie
import com.machioni.mpvmovies.domain.model.Page

fun MovieSearchRM.toDomainModel() = Page(
        moviesPage?.map { it.toDomainModel() } ?: emptyList(),
        totalResults?.toLong() ?: 0L
)

fun MovieRM.toDomainModel() = Movie(title, year, imdbId, posterUrl)

fun DetailedMovieRM.toDomainModel() = DetailedMovie(title, year, imdbId, posterUrl, plot, country, duration, imdbRating)