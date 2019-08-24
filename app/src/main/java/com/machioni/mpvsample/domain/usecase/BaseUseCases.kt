package com.machioni.mpvsample.domain.usecase

import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single

abstract class SingleUseCase<Response, in Request>(private val executorScheduler: Scheduler, private val postExecutionScheduler: Scheduler) {
    protected abstract fun getRawSingle(params: Request): Single<Response>
    fun getSingle(request: Request): Single<Response> = getRawSingle(request).subscribeOn(executorScheduler).observeOn(postExecutionScheduler)
}

abstract class CompletableUseCase<in Request>(private val executorScheduler: Scheduler, private val postExecutionScheduler: Scheduler) {
    protected abstract fun getRawCompletable(params: Request): Completable
    fun getCompletable(request: Request): Completable = getRawCompletable(request).subscribeOn(executorScheduler).observeOn(postExecutionScheduler)
}