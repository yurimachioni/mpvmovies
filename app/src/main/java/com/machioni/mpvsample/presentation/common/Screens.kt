package com.machioni.mpvsample.presentation.common

import androidx.fragment.app.Fragment
import com.machioni.mpvsample.presentation.scene.moviedetail.MovieDetailFragment
import com.machioni.mpvsample.presentation.scene.movielist.MovieListFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

sealed class Screen : SupportAppScreen()

class MovieListScreen : Screen() {
    override fun getFragment(): Fragment = MovieListFragment.newInstance()
}

class MovieDetailScreen(private val movieId: Int) : Screen() {
    override fun getFragment(): Fragment = MovieDetailFragment.newInstance(movieId)
}