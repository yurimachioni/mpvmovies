package com.machioni.mpvsample.presentation.scene.moviedetail

import com.evernote.android.state.State
import com.machioni.mpvsample.common.MyApplication
import com.machioni.mpvsample.domain.usecase.GetMovieById
import com.machioni.mpvsample.presentation.common.BackButtonListener
import com.machioni.mpvsample.presentation.common.BaseFragment
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class MovieDetailFragment : BaseFragment(), BackButtonListener {

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
        fun newInstance(id: Int): MovieDetailFragment = MovieDetailFragment().apply{ movieId = id }
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