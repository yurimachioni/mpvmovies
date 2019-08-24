package com.machioni.libermovies.presentation.common

import androidx.fragment.app.Fragment
import com.machioni.libermovies.presentation.scene.moviedetail.MovieDetailPresenter
import com.machioni.libermovies.presentation.scene.movielist.MovieListPresenter
import ru.terrakok.cicerone.android.support.SupportAppScreen

sealed class Screen : SupportAppScreen()

class MovieListScreen : Screen() {
    override fun getFragment(): Fragment = MovieListPresenter.newInstance()
}

class MovieDetailScreen(private val movieId: Int) : Screen() {
    override fun getFragment(): Fragment = MovieDetailPresenter.newInstance(movieId)
}