package com.machioni.mpvsample.presentation.scene.movielist

import com.machioni.mpvsample.presentation.common.BaseView
import com.machioni.mpvsample.presentation.scene.moviedetail.MovieDetailVM
import io.reactivex.Observable

interface MovieListView : BaseView {
    val onItemClickedObservable: Observable<Int>

    fun displayMovies(list: List<MovieListVM>)
    fun displayToast(text: String)
    fun displayLoading()
}