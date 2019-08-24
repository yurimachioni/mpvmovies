package com.machioni.libermovies.presentation.scene.movielist

import com.machioni.libermovies.presentation.common.BaseView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

interface MovieListView : BaseView {
    val itemClicksObservable: Observable<String>
    val searchChangesSubject: PublishSubject<String>

    fun displayMovies(list: List<MovieVM>)
    fun displayToast(text: String)
    fun displayLoading()
    fun dismissLoading()
}