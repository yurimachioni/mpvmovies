package com.machioni.libermovies.presentation.scene.movielist

import com.machioni.libermovies.common.MyApplication
import com.machioni.libermovies.domain.model.Movie
import com.machioni.libermovies.domain.usecase.GetMovies
import com.machioni.libermovies.presentation.common.BackButtonListener
import com.machioni.libermovies.presentation.common.BasePresenter
import com.machioni.libermovies.presentation.common.MovieDetailScreen
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MovieListPresenter : BasePresenter(), BackButtonListener {

    @Inject
    override lateinit var view: MovieListView

    @Inject
    lateinit var getMovies: GetMovies

    init{
        MyApplication.daggerComponent.inject(this)
    }

    companion object {
        fun newInstance(): MovieListPresenter = MovieListPresenter()
    }

    override fun onFirstLoad() {
        bindToView()
    }

    private fun bindToView(){
        view.itemClicksObservable.subscribe{ movieId ->
            router.navigateTo(MovieDetailScreen(movieId))
        }.addTo(disposables)

        view.searchChangesSubject
                .debounce(1000, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .filter{ it.length > 2 }
                .distinctUntilChanged()
                .switchMapCompletable { getMovies.getSingle(it)
                        .map { it.map(Movie::toViewModel) }
                        .doOnSubscribe { view.displayLoading() }
                        .delayUntilActive()
                        .doOnSuccess { view.displayMovies(it) }
                        .doOnError { }
                        .doFinally { view.dismissLoading() }
                        .ignoreElement()
                        .onErrorComplete()
                }.subscribe()
                .addTo(disposables)
    }

}