package com.machioni.mpvmovies.domain.usecase

import com.machioni.mpvmovies.common.di.BackgroundScheduler
import com.machioni.mpvmovies.common.di.MainScheduler
import com.machioni.mpvmovies.domain.model.Movie
import com.machioni.mpvmovies.domain.repositoryinterface.MoviesRepositoryInterface
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class RemoveMovieFromFavorites @Inject constructor(@BackgroundScheduler executorScheduler: Scheduler,
                                                   @MainScheduler postExecutionScheduler: Scheduler,
                                                   private val moviesRepositoryInterface: MoviesRepositoryInterface)
    : CompletableUseCase<String>(executorScheduler, postExecutionScheduler) {

    override fun getRawCompletable(param: String)= moviesRepositoryInterface.removeMovieFromFavorites(param)

}