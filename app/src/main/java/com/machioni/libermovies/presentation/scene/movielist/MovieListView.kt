package com.machioni.libermovies.presentation.scene.movielist

import com.machioni.libermovies.presentation.common.BaseView
import io.reactivex.Observable

interface MovieListView : BaseView {
    val onItemClickedObservable: Observable<Int>

    fun displayMovies(list: List<MovieListVM>)
    fun displayToast(text: String)
    fun displayLoading()
}