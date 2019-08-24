package com.machioni.libermovies.presentation.scene.movielist

import com.machioni.libermovies.presentation.common.BaseView
import io.reactivex.Observable

interface MovieListView : BaseView {
    val onItemClickedObservable: Observable<String>

    fun displayMovies(list: List<MovieVM>)
    fun displayToast(text: String)
    fun displayLoading()
}