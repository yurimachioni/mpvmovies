package com.machioni.libermovies.domain.usecase

import com.machioni.libermovies.common.di.BackgroundScheduler
import com.machioni.libermovies.common.di.MainScheduler
import com.machioni.libermovies.domain.model.Movie
import com.machioni.libermovies.domain.repositoryinterface.MoviesRepositoryInterface
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class GetMovies @Inject constructor(@BackgroundScheduler executorScheduler: Scheduler,
                                    @MainScheduler postExecutionScheduler: Scheduler,
                                    private val moviesRepositoryInterface: MoviesRepositoryInterface)
    : SingleUseCase<List<Movie>, String>(executorScheduler, postExecutionScheduler) {

    override fun getRawSingle(param: String): Single<List<Movie>> {
        return moviesRepositoryInterface.getMovies(param)
                .zipWith(moviesRepositoryInterface.getFavoriteMovies(),
                        BiFunction { movies: List<Movie>, favoriteIds: Set<String> ->
                            movies.map { it.copy(isFavorite = favoriteIds.contains(it.imdbId)) }
                        })
    }
}