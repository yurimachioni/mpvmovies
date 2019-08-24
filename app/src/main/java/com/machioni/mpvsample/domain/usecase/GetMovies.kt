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

class GetMovies @Inject constructor(@BackgroundScheduler executorScheduler: Scheduler,
                                   @MainScheduler postExecutionScheduler: Scheduler) : SingleUseCase<List<Movie>, Unit>(executorScheduler, postExecutionScheduler) {

    override fun getRawSingle(params: Unit): Single<List<Movie>> =
            Single.just((11..41).toList()
                    .map{ generatedId ->
                        Movie(generatedId)
                    }).delay(3000, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
}