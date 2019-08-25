package com.machioni.libermovies.data.repository

import com.machioni.libermovies.data.local.datasource.MoviesPersistentDataSource
import com.machioni.libermovies.data.mapper.toDomainModel
import com.machioni.libermovies.data.remote.datasource.MoviesRemoteDataSource
import com.machioni.libermovies.data.remote.model.DetailedMovieRM
import com.machioni.libermovies.data.remote.model.MovieRM
import com.machioni.libermovies.domain.model.DetailedMovie
import com.machioni.libermovies.domain.model.Movie
import com.machioni.libermovies.domain.repositoryinterface.MoviesRepositoryInterface
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val moviesRemoteDataSource: MoviesRemoteDataSource,
                                           private val moviesPersistentDataSource: MoviesPersistentDataSource) : MoviesRepositoryInterface{

    override fun getMovies(searchParam: String) : Single<List<Movie>> {
        return moviesRemoteDataSource.getMovies(searchParam).map { it.search?.map(MovieRM::toDomainModel) ?: emptyList() }
    }

    override fun getMovieDetails(id: String): Single<DetailedMovie> {
        return moviesRemoteDataSource.getMovieDetails(id).map(DetailedMovieRM::toDomainModel)
    }

    override fun getFavoriteMovies(): Single<Set<String>> {
        return moviesPersistentDataSource.getFavoriteMovies()
    }

    override fun addMovieToFavorites(id: String): Completable {
        return moviesPersistentDataSource.addMovieToFavorites(id)
    }

    override fun removeMovieFromFavorites(id: String): Completable {
        return moviesPersistentDataSource.removeMovieFromFavorits(id)
    }
}