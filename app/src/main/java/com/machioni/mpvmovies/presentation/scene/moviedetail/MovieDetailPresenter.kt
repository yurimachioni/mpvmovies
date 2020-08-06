package com.machioni.mpvmovies.presentation.scene.moviedetail

import com.evernote.android.state.State
import com.machioni.mpvmovies.common.MyApplication
import com.machioni.mpvmovies.common.di.ApplicationComponent
import com.machioni.mpvmovies.domain.model.DetailedMovie
import com.machioni.mpvmovies.domain.usecase.GetMovieById
import com.machioni.mpvmovies.presentation.common.BackButtonListener
import com.machioni.mpvmovies.presentation.common.BasePresenter
import io.reactivex.Single
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class MovieDetailPresenter : BasePresenter(), BackButtonListener {

    @Inject
    override lateinit var view: MovieDetailView

    val getMovieById: GetMovieById

    @State
    var movieId = "-1"

    init{
        val component : ApplicationComponent = MyApplication.daggerComponent
        component.inject(this)

        getMovieById = component.curryGetMovieById()
    }

    companion object {
        fun newInstance(id: String): MovieDetailPresenter = MovieDetailPresenter().apply{ movieId = id }
    }

    override fun onFirstLoad() {
        hideKeyboard()
        getMovieById()
    }

    private fun getMovieById(){
        getMovieById(movieId)
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