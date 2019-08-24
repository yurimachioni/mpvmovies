package com.machioni.libermovies.domain.repositoryinterface

import com.machioni.libermovies.domain.model.DetailedMovie
import com.machioni.libermovies.domain.model.Movie
import io.reactivex.Single

interface MoviesRepositoryInterface{
    fun getMovies(searchParam: String) : Single<List<Movie>>
    fun getMovieDetails(id: String) : Single<DetailedMovie>
}