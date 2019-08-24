package com.machioni.mpvsample.presentation.scene.moviedetail

import com.machioni.mpvsample.presentation.common.BaseView

interface MovieDetailView : BaseView {
    fun displayMovie(movieDetail: MovieDetailVM)
    fun displayLoading()
}