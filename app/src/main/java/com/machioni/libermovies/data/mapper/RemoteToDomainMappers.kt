package com.machioni.libermovies.data.mapper

import com.machioni.libermovies.data.remote.model.DetailedMovieRM
import com.machioni.libermovies.data.remote.model.MovieRM
import com.machioni.libermovies.domain.model.DetailedMovie
import com.machioni.libermovies.domain.model.Movie

fun MovieRM.toDomainModel() = Movie(title, year, imdbId, posterUrl)

fun DetailedMovieRM.toDomainModel() = DetailedMovie(title, year, imdbId, posterUrl, plot, country, duration, imdbRating)