package com.machioni.mpvmovies.presentation.scene.movielist

import com.machioni.mpvmovies.common.MOVIE_PAGE_SIZE
import com.machioni.mpvmovies.common.MyApplication
import com.machioni.mpvmovies.common.di.MainScheduler
import com.machioni.mpvmovies.domain.usecase.AddMovieToFavorites
import com.machioni.mpvmovies.domain.usecase.GetMovies
import com.machioni.mpvmovies.domain.usecase.RemoveMovieFromFavorites
import com.machioni.mpvmovies.presentation.common.BackButtonListener
import com.machioni.mpvmovies.presentation.common.BasePresenter
import com.machioni.mpvmovies.presentation.common.MovieDetailScreen
import io.reactivex.Completable
import io.reactivex.Scheduler
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

    @Inject
    @field:MainScheduler
    lateinit var delayScheduler: Scheduler

    private var totalResults = 0L

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
            view.clearFocus()
            router.navigateTo(MovieDetailScreen(movieId))
        }.addTo(disposables)

        view.searchChangesSubject
                .debounce(600, TimeUnit.MILLISECONDS, delayScheduler)
                .filter { it.length > 2 }
                .distinctUntilChanged()
                .switchMapCompletable { searchQuery ->
                    searchMovies(searchQuery, 1)
                }.subscribe()
                .addTo(disposables)

        view.listEndReachedObservable.flatMapCompletable { listSize ->
            if(listSize < totalResults){
                val searchQuery = view.searchChangesSubject.value ?: ""
                val nextPage = (listSize / MOVIE_PAGE_SIZE) + 1
                searchMovies(searchQuery, nextPage)
            } else Completable.complete()
        }.subscribe().addTo(disposables)

        view.favoriteClicksObservable.flatMapCompletable {
            (if (it.isFavorite)
                removeMovieFromFavorites.getCompletable(it.imdbId)
            else
                addMoviesToFavorites.getCompletable(it.imdbId))
                    .delayUntilActive()
                    .doOnComplete { view.updateMovie(it.copy(isFavorite = !it.isFavorite)) }
                    .doOnError { view.displayToast("Can't set favorite") }
                    .onErrorComplete()
        }.subscribe().addTo(disposables)
    }

    private fun searchMovies(searchQuery: String, page: Long) : Completable {
        return getMovies.getSingle(GetMovies.Request(searchQuery, page))
                .map { it.toViewModel() }
                .doOnSubscribe { view.displayLoading() }
                .delayUntilActive()
                .doOnSuccess { moviesPage ->
                    totalResults = moviesPage.totalResults
                    if (moviesPage.items.isEmpty())
                        view.displayEmptyState()
                    else {
                        if(page == 1L)
                            view.displayMovies(moviesPage.items)
                        else
                            view.addMovies(moviesPage.items)
                    }
                }
                .doOnError { view.displayError() }
                .doFinally { view.dismissLoading() }
                .ignoreElement()
                .onErrorComplete()
    }

}