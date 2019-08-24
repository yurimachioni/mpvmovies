package com.machioni.mpvsample.presentation.scene.movielist

import com.evernote.android.state.State
import com.machioni.mpvsample.common.MyApplication
import com.machioni.mpvsample.domain.model.Movie
import com.machioni.mpvsample.domain.usecase.GetMovies
import com.machioni.mpvsample.presentation.common.BackButtonListener
import com.machioni.mpvsample.presentation.common.BaseFragment
import com.machioni.mpvsample.presentation.common.MovieDetailScreen
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class MovieListFragment : BaseFragment(), BackButtonListener {

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
        fun newInstance(): MovieListFragment = MovieListFragment()
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
        getMovies.getSingle(Unit)
                .map { it.map(Movie::toViewModel) }
                .delayUntilActive()
                .subscribe({
                    view.displayMovies(it)
                }, {
                    view.displayToast("Erro")
                }).addTo(disposables)
    }

}