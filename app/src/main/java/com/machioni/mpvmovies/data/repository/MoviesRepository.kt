package com.machioni.mpvmovies.data.repository

import com.machioni.mpvmovies.data.local.datasource.MoviesPersistentDataSource
import com.machioni.mpvmovies.data.mapper.toDomainModel
import com.machioni.mpvmovies.data.remote.datasource.MoviesRemoteDataSource
import com.machioni.mpvmovies.data.remote.model.DetailedMovieRM
import com.machioni.mpvmovies.data.remote.model.MovieRM
import com.machioni.mpvmovies.data.remote.model.MovieSearchRM
import com.machioni.mpvmovies.domain.model.DetailedMovie
import com.machioni.mpvmovies.domain.model.Movie
import com.machioni.mpvmovies.domain.model.Page
import com.machioni.mpvmovies.domain.repositoryinterface.MoviesRepositoryInterface
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val moviesRemoteDataSource: MoviesRemoteDataSource,
                                           private val moviesPersistentDataSource: MoviesPersistentDataSource) : MoviesRepositoryInterface{

    override fun searchMovies(searchParam: String, page: Long) : Single<Page<Movie>> {
        return moviesRemoteDataSource.getMovies(searchParam, page).map(MovieSearchRM::toDomainModel)
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
        return moviesPersistentDataSource.removeMovieFromFavorites(id)
    }
}