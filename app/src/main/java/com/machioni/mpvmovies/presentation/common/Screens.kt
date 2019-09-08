package com.machioni.mpvmovies.presentation.common

import androidx.fragment.app.Fragment
import com.machioni.mpvmovies.presentation.scene.moviedetail.MovieDetailPresenter
import com.machioni.mpvmovies.presentation.scene.movielist.MovieListPresenter
import ru.terrakok.cicerone.android.support.SupportAppScreen

sealed class Screen : SupportAppScreen()

class MovieListScreen : Screen() {
    override fun getFragment(): Fragment = MovieListPresenter.newInstance()
}

class MovieDetailScreen(private val movieId: String) : Screen() {
    override fun getFragment(): Fragment = MovieDetailPresenter.newInstance(movieId)
}