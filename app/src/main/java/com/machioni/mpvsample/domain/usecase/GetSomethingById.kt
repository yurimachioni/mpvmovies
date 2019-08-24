package com.machioni.mpvsample.domain.usecase

import com.machioni.mpvsample.common.di.BackgroundScheduler
import com.machioni.mpvsample.common.di.MainScheduler
import com.machioni.mpvsample.domain.model.Something
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import java.util.logging.Logger
import javax.inject.Inject

class GetSomethingById @Inject constructor(@BackgroundScheduler executorScheduler: Scheduler,
                                           @MainScheduler postExecutionScheduler: Scheduler) : SingleUseCase<Something, Int>(executorScheduler, postExecutionScheduler) {

    override fun getRawSingle(params: Int): Single<Something> = Single.just(Something(params)).delay(500, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
}