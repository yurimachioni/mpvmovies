package com.machioni.mpvmovies.presentation.common

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.machioni.mpvmovies.common.rx.DisposableHolder
import com.machioni.mpvmovies.common.rx.DisposableHolderDelegate
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import ru.terrakok.cicerone.Router


abstract class BasePresenter : Fragment(), BackButtonListener, DisposableHolder by DisposableHolderDelegate() {

    abstract val view: BaseView
    val router: Router by lazy { (activity as FlowProvider).getRouter() }
    var firstLoad = true

    val lifecycleSubject = BehaviorSubject.create<Lifecycle.Event>()

    init {
        lifecycle.addObserver(object: LifecycleEventObserver{
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                lifecycleSubject.onNext(event)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return view.inflate(context!!, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.view.initViews()

        if (firstLoad) {
            firstLoad = false
            onFirstLoad()
        }
    }

    //will be called only on the first onViewCreated of this instance
    open fun onFirstLoad() {}

    override fun onDestroy() {
        disposeAll()
        super.onDestroy()
    }

    override fun onBackPressed(): Boolean {
        router.exit()
        return true
    }

    fun <T> Flowable<T>.delayUntilActive(): Flowable<T> {
        return this.onErrorResumeNext { error: Throwable ->
            Flowable.error<T>(error).delaySubscription (
                    lifecycleSubject.toFlowable(BackpressureStrategy.LATEST).filter { it.isActive }
            )
        }.delay { lifecycleSubject.toFlowable(BackpressureStrategy.LATEST).filter { it.isActive } }
    }

    //TODO find a better way for Single that doesn't rely on converting to flowable. May be impossible.
    fun <T> Single<T>.delayUntilActive(): Single<T> = toFlowable().delayUntilActive().firstOrError()

    //TODO find a better way for Completable that doesn't rely on converting to single. Straight conversion to flowable doesn't work for some reason
    fun Completable.delayUntilActive(): Completable = toSingleDefault(Unit).delayUntilActive().ignoreElement()

    fun hideKeyboard() {
        val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
        var view = activity?.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}