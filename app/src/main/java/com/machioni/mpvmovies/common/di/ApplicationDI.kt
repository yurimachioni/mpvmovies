package com.machioni.mpvmovies.common.di

import android.content.Context
import com.machioni.mpvmovies.data.repository.MoviesRepository
import com.machioni.mpvmovies.domain.model.DetailedMovie
import com.machioni.mpvmovies.domain.repositoryinterface.MoviesRepositoryInterface
import com.machioni.mpvmovies.domain.usecase.getMovieById
import com.machioni.mpvmovies.presentation.scene.moviedetail.MovieDetailPresenter
import com.machioni.mpvmovies.presentation.scene.moviedetail.MovieDetailUI
import com.machioni.mpvmovies.presentation.scene.moviedetail.MovieDetailView
import com.machioni.mpvmovies.presentation.scene.movielist.MovieListPresenter
import com.machioni.mpvmovies.presentation.scene.movielist.MovieListUI
import com.machioni.mpvmovies.presentation.scene.movielist.MovieListView
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @BackgroundScheduler
    fun backgroundScheduler() : Scheduler = Schedulers.io()

    @Provides
    @MainScheduler
    fun mainScheduler() : Scheduler = AndroidSchedulers.mainThread()

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

    @Provides
    fun curryGetMovieByid(moviesRepository: MoviesRepository) : (String) -> Single<DetailedMovie> {
        return { id -> getMovieById(id, moviesRepository) }
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