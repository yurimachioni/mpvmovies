package com.machioni.mpvmovies.presentation.scene.moviedetail

import com.evernote.android.state.State
import com.machioni.mpvmovies.common.MyApplication
import com.machioni.mpvmovies.domain.usecase.GetMovieById
import com.machioni.mpvmovies.presentation.common.BackButtonListener
import com.machioni.mpvmovies.presentation.common.BasePresenter
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class MovieDetailPresenter : BasePresenter(), BackButtonListener {

    @Inject
    override lateinit var view: MovieDetailView

    @Inject
    lateinit var getMovieById: GetMovieById

    @State
    var movieId = "-1"

    init{
        MyApplication.daggerComponent.inject(this)
    }

    companion object {
        fun newInstance(id: String): MovieDetailPresenter = MovieDetailPresenter().apply{ movieId = id }
    }

    override fun onFirstLoad() {
        hideKeyboard()
        getMovieById()
    }

    private fun getMovieById(){
        getMovieById.getSingle(movieId)
                .map { it.toViewModel() }
                .doOnSubscribe { view.displayLoading() }
                .delayUntilActive()
                .doFinally { view.dismissLoading() }
                .subscribe({
                    view.displayMovie(it)
                }, {
                    view.displayError()
                }).addTo(disposables)
    }
}