package com.machioni.mpvmovies.presentation.scene.moviedetail

import com.machioni.mpvmovies.presentation.common.BaseView

interface MovieDetailView : BaseView {
    fun displayMovie(detailedMovie: DetailedMovieVM)
    fun displayError()
    fun displayLoading()
    fun dismissLoading()
}