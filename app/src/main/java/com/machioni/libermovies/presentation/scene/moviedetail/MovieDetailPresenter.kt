package com.machioni.libermovies.presentation.scene.moviedetail

import com.evernote.android.state.State
import com.machioni.libermovies.common.MyApplication
import com.machioni.libermovies.domain.usecase.GetMovieById
import com.machioni.libermovies.presentation.common.BackButtonListener
import com.machioni.libermovies.presentation.common.BasePresenter
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class MovieDetailPresenter : BasePresenter(), BackButtonListener {

    @Inject
    override lateinit var view: MovieDetailView

    @Inject
    lateinit var getMovieById: GetMovieById

    @State
    var movieId = 1

    init{
        MyApplication.daggerComponent.inject(this)
    }

    companion object {
        fun newInstance(id: Int): MovieDetailPresenter = MovieDetailPresenter().apply{ movieId = id }
    }

    override fun onFirstLoad() {
        getMovieById()
    }

    private fun getMovieById(){
        view.displayLoading()
        getMovieById.getSingle(movieId)
                .map { it.toViewModel() }
                .delayUntilActive()
                .subscribe({
                    view.displayMovie(it)
                }, {

                }).addTo(disposables)
    }
}