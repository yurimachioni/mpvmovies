package com.machioni.libermovies.presentation.scene.moviedetail

import com.machioni.libermovies.presentation.common.BaseView

interface MovieDetailView : BaseView {
    fun displayMovie(detailedMovie: DetailedMovieVM)
    fun displayLoading()
}