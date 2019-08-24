package com.machioni.libermovies.presentation.scene.movielist

import com.evernote.android.state.State
import com.machioni.libermovies.common.MyApplication
import com.machioni.libermovies.domain.model.Movie
import com.machioni.libermovies.domain.usecase.GetMovies
import com.machioni.libermovies.presentation.common.BackButtonListener
import com.machioni.libermovies.presentation.common.BasePresenter
import com.machioni.libermovies.presentation.common.MovieDetailScreen
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class MovieListPresenter : BasePresenter(), BackButtonListener {

    @Inject
    override lateinit var view: MovieListView

    @Inject
    lateinit var getMovies: GetMovies

    @State
    var totalItemsViewed = 1

    init{
        MyApplication.daggerComponent.inject(this)
    }

    companion object {
        fun newInstance(): MovieListPresenter = MovieListPresenter()
    }

    override fun onFirstLoad() {
        bindToView()
        getMovies()
    }

    private fun bindToView(){
        view.onItemClickedObservable.subscribe{ movieId ->
            view.displayToast("total items viewed: ${totalItemsViewed++}")
            router.navigateTo(MovieDetailScreen(movieId))
        }.addTo(disposables)
    }

    private fun getMovies(){
        view.displayLoading()
        getMovies.getSingle("tita")
                .map { it.map(Movie::toViewModel) }
                .delayUntilActive()
                .subscribe({
                    view.displayMovies(it)
                }, {
                    view.displayToast("Erro")
                }).addTo(disposables)
    }

}