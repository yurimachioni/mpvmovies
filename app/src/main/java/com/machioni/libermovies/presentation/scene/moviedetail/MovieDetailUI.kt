package com.machioni.libermovies.presentation.scene.moviedetail

import android.view.View
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.machioni.libermovies.R
import com.machioni.libermovies.presentation.common.BaseUI
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import kotlinx.android.synthetic.main.loading.*
import javax.inject.Inject

class MovieDetailUI @Inject constructor() : BaseUI(), MovieDetailView {

    @LayoutRes
    override val layoutId = R.layout.fragment_movie_detail

    override fun initViews() {

    }

    override fun displayMovie(detailedMovie: DetailedMovieVM){
        loadingLayout?.visibility = View.GONE
        with(detailedMovie) {
            Glide.with(context).load(posterUrl).into(posterImageView)
            titleTextView.text = title
            sinopsisTextView.text = plot
            yearTextView.text = context.getString(R.string.year, year)
            durationTextView.text = duration
        }
    }

    override fun displayLoading() {
        loadingLayout?.visibility = View.VISIBLE
    }
}