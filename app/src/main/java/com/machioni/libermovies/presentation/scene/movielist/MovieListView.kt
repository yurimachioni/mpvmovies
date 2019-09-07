package com.machioni.libermovies.presentation.scene.movielist

import com.machioni.libermovies.presentation.common.BaseView
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

interface MovieListView : BaseView {
    val itemClicksObservable: Observable<String>
    val searchChangesSubject: BehaviorSubject<String>
    val favoriteClicksObservable: Observable<MovieVM>
    val listEndReachedObservable: Observable<Long>

    fun updateMovie(movieVM: MovieVM)
    fun displayMovies(movies: List<MovieVM>)
    fun addMovies(movies: List<MovieVM>)
    fun displayInstructions()
    fun displayEmptyState()
    fun displayError()
    fun displayLoading()
    fun dismissLoading()
}