package com.machioni.libermovies.data.repository

import com.machioni.libermovies.data.mapper.toDomainModel
import com.machioni.libermovies.data.remote.datasource.MoviesRemoteDataSource
import com.machioni.libermovies.data.remote.model.DetailedMovieRM
import com.machioni.libermovies.data.remote.model.MovieRM
import com.machioni.libermovies.domain.model.DetailedMovie
import com.machioni.libermovies.domain.model.Movie
import com.machioni.libermovies.domain.repositoryinterface.MoviesRepositoryInterface
import io.reactivex.Single
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val moviesRemoteDataSource: MoviesRemoteDataSource) : MoviesRepositoryInterface{

    override fun getMovies(searchParam: String) : Single<List<Movie>> {
        return moviesRemoteDataSource.getMovies(searchParam).map { it.search.map(MovieRM::toDomainModel) }
    }

    override fun getMovieDetails(id: String): Single<DetailedMovie> {
        return moviesRemoteDataSource.getMovieDetails(id).map(DetailedMovieRM::toDomainModel)
    }
}