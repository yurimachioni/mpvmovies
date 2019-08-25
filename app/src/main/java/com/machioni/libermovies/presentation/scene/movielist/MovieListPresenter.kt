package com.machioni.libermovies.presentation.scene.movielist

import com.machioni.libermovies.common.MyApplication
import com.machioni.libermovies.domain.model.Movie
import com.machioni.libermovies.domain.usecase.AddMovieToFavorites
import com.machioni.libermovies.domain.usecase.GetMovies
import com.machioni.libermovies.domain.usecase.RemoveMovieFromFavorites
import com.machioni.libermovies.presentation.common.BackButtonListener
import com.machioni.libermovies.presentation.common.BasePresenter
import com.machioni.libermovies.presentation.common.MovieDetailScreen
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_movie_list.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MovieListPresenter : BasePresenter(), BackButtonListener {

    @Inject
    override lateinit var view: MovieListView

    @Inject
    lateinit var getMovies: GetMovies

    @Inject
    lateinit var addMoviesToFavorites: AddMovieToFavorites

    @Inject
    lateinit var removeMovieFromFavorites: RemoveMovieFromFavorites

    init {
        MyApplication.daggerComponent.inject(this)
    }

    companion object {
        fun newInstance(): MovieListPresenter = MovieListPresenter()
    }

    override fun onFirstLoad() {
        bindToView()
        view.displayInstructions()
    }

    private fun bindToView() {
        view.itemClicksObservable.subscribe { movieId ->
            searchEditText.clearFocus()
            router.navigateTo(MovieDetailScreen(movieId))
        }.addTo(disposables)

        view.searchChangesSubject
                .debounce(600, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .filter { it.length > 2 }
                .distinctUntilChanged()
                .switchMapCompletable {
                    getMovies.getSingle(it)
                            .map { it.map(Movie::toViewModel) }
                            .doOnSubscribe { view.displayLoading() }
                            .delayUntilActive()
                            .doOnSuccess { movies ->
                                if (movies.isEmpty())
                                    view.displayEmptyState()
                                else
                                    view.displayMovies(movies)
                            }
                            .doOnError { view.displayError() }
                            .doFinally { view.dismissLoading() }
                            .ignoreElement()
                            .onErrorComplete()
                }.subscribe()
                .addTo(disposables)

        view.favoriteClicksObservable.flatMapCompletable {
            (if (it.isFavorite)
                removeMovieFromFavorites.getCompletable(it.imdbId)
            else
                addMoviesToFavorites.getCompletable(it.imdbId))
                    .delayUntilActive()
                    .doOnComplete { view.updateMovie(it.copy(isFavorite = !it.isFavorite)) }
                    .onErrorComplete()
        }.subscribe().addTo(disposables)
    }

}