package com.machioni.mpvmovies.domain.usecase

import com.machioni.mpvmovies.domain.model.DetailedMovie
import com.machioni.mpvmovies.domain.repositoryinterface.MoviesRepositoryInterface
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

inline class GetMovieById(override val func: (String) -> Single<DetailedMovie>) : UseCase<String, Single<DetailedMovie>>

fun getMovieById(id: String, moviesRepository: MoviesRepositoryInterface): Single<DetailedMovie> {
    return moviesRepository.getMovieDetails(id)
            .zipWith(moviesRepository.getFavoriteMovies(),
                    BiFunction { movie: DetailedMovie, favoriteIds: Set<String> ->
                        movie.copy(isFavorite = favoriteIds.contains(movie.imdbId))
                    })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}