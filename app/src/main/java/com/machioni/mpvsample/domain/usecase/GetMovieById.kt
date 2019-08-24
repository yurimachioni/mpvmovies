package com.machioni.mpvsample.domain.usecase

import com.machioni.mpvsample.common.di.BackgroundScheduler
import com.machioni.mpvsample.common.di.MainScheduler
import com.machioni.mpvsample.domain.model.Movie
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import java.util.logging.Logger
import javax.inject.Inject

class GetMovieById @Inject constructor(@BackgroundScheduler executorScheduler: Scheduler,
                                           @MainScheduler postExecutionScheduler: Scheduler) : SingleUseCase<Movie, Int>(executorScheduler, postExecutionScheduler) {

    override fun getRawSingle(params: Int): Single<Movie> = Single.just(Movie(params)).delay(500, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
}