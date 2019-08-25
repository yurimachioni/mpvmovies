package com.machioni.libermovies.domain.usecase

import com.machioni.libermovies.common.di.BackgroundScheduler
import com.machioni.libermovies.common.di.MainScheduler
import com.machioni.libermovies.domain.model.Movie
import com.machioni.libermovies.domain.repositoryinterface.MoviesRepositoryInterface
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