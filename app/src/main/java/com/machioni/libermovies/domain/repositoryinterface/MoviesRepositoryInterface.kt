package com.machioni.libermovies.domain.repositoryinterface

import com.machioni.libermovies.domain.model.DetailedMovie
import com.machioni.libermovies.domain.model.Movie
import com.machioni.libermovies.domain.model.Page
import io.reactivex.Completable
import io.reactivex.Single

interface MoviesRepositoryInterface{
    fun searchMovies(searchParam: String, page: Long) : Single<Page<Movie>>
    fun getMovieDetails(id: String) : Single<DetailedMovie>
    fun getFavoriteMovies() : Single<Set<String>>
    fun addMovieToFavorites(id: String) : Completable
    fun removeMovieFromFavorites(id: String) : Completable
}