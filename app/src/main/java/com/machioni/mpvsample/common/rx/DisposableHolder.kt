package com.machioni.mpvsample.common.rx

import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

interface DisposableHolder {
    var disposables: CompositeDisposable

    fun disposeAll()
}

class DisposableHolderDelegate @Inject constructor() : DisposableHolder {
    override var disposables = CompositeDisposable()

    override fun disposeAll() {
        disposables.dispose()
        disposables = CompositeDisposable()
    }
}