package com.machioni.libermovies.common.di

import android.content.Context
import com.machioni.libermovies.data.repository.MoviesRepository
import com.machioni.libermovies.domain.repositoryinterface.MoviesRepositoryInterface
import com.machioni.libermovies.presentation.scene.moviedetail.MovieDetailPresenter
import com.machioni.libermovies.presentation.scene.moviedetail.MovieDetailUI
import com.machioni.libermovies.presentation.scene.moviedetail.MovieDetailView
import com.machioni.libermovies.presentation.scene.movielist.MovieListPresenter
import com.machioni.libermovies.presentation.scene.movielist.MovieListUI
import com.machioni.libermovies.presentation.scene.movielist.MovieListView
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @BackgroundScheduler
    fun backgroundScheduler() = Schedulers.io()

    @Provides
    @Singleton
    @MainScheduler
    fun mainScheduler() = AndroidSchedulers.mainThread()

    @Provides
    fun movieDetailView(movieDetailUI: MovieDetailUI): MovieDetailView {
        return movieDetailUI
    }

    @Provides
    fun movieListView(movieListUI: MovieListUI): MovieListView {
        return movieListUI
    }

    @Provides
    @Singleton
    fun moviesRepository(moviesRepository: MoviesRepository): MoviesRepositoryInterface {
        return moviesRepository
    }
}

@Singleton
@Component(modules = [ApplicationModule::class, NetworkModule::class])
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }

    @BackgroundScheduler
    fun backgroundScheduler(): Scheduler

    @MainScheduler
    fun mainScheduler(): Scheduler

    fun inject(movieDetailFragment: MovieDetailPresenter)
    fun inject(movieListPresenter: MovieListPresenter)
}