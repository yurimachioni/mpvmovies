package com.machioni.libermovies.presentation.scene.movielist

import com.machioni.libermovies.presentation.common.BaseView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

interface MovieListView : BaseView {
    val itemClicksObservable: Observable<String>
    val searchChangesSubject: PublishSubject<String>
    val favoriteClicksObservable: Observable<MovieVM>

    fun updateMovie(movieVM: MovieVM)
    fun displayMovies(list: List<MovieVM>)
    fun displayInstructions()
    fun displayEmptyState()
    fun displayError()
    fun displayLoading()
    fun dismissLoading()
}