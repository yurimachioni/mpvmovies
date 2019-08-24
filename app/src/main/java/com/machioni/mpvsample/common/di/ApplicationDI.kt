package com.machioni.mpvsample.common.di

import android.content.Context
import com.machioni.mpvsample.presentation.scene.moviedetail.MovieDetailFragment
import com.machioni.mpvsample.presentation.scene.moviedetail.MovieDetailView
import com.machioni.mpvsample.presentation.scene.moviedetail.MovieDetailUI
import com.machioni.mpvsample.presentation.scene.movielist.MovieListFragment
import com.machioni.mpvsample.presentation.scene.movielist.MovieListView
import com.machioni.mpvsample.presentation.scene.movielist.MovieListUI
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module
class ApplicationModule(private val context: Context) {
    @Provides
    @Singleton
    @ApplicationContext
    fun context() = context

    @Provides
    @BackgroundScheduler
    fun backgroundScheduler() = Schedulers.io()

    @Provides
    @Singleton
    @MainScheduler
    fun mainScheduler() = AndroidSchedulers.mainThread()

    @Provides
    fun movieDetailView(movieDetailUI: MovieDetailUI) : MovieDetailView{
        return movieDetailUI
    }

    @Provides
    fun movieListView(movieListUI: MovieListUI) : MovieListView{
        return movieListUI
    }
}

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    @ApplicationContext
    fun context(): Context

    @BackgroundScheduler
    fun backgroundScheduler(): Scheduler

    @MainScheduler
    fun mainScheduler(): Scheduler

    fun inject(movieDetailFragment: MovieDetailFragment)
    fun inject(movieListFragment: MovieListFragment)
}