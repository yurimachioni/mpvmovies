package com.machioni.mpvmovies.domain.usecase

import com.machioni.mpvmovies.common.di.BackgroundScheduler
import com.machioni.mpvmovies.common.di.MainScheduler
import com.machioni.mpvmovies.domain.model.DetailedMovie
import com.machioni.mpvmovies.domain.model.Movie
import com.machioni.mpvmovies.domain.repositoryinterface.MoviesRepositoryInterface
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GetMovieById @Inject constructor(@BackgroundScheduler executorScheduler: Scheduler,
                                       @MainScheduler postExecutionScheduler: Scheduler,
                                       private val moviesRepositoryInterface: MoviesRepositoryInterface)
    : SingleUseCase<DetailedMovie, String>(executorScheduler, postExecutionScheduler) {

    override fun getRawSingle(param: String): Single<DetailedMovie> {
        return moviesRepositoryInterface.getMovieDetails(param)
                .zipWith(moviesRepositoryInterface.getFavoriteMovies(),
                        BiFunction { movie: DetailedMovie, favoriteIds: Set<String> ->
                            movie.copy(isFavorite = favoriteIds.contains(movie.imdbId))
                        })
    }
}