package com.machioni.libermovies.common.di

import android.content.Context
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.machioni.libermovies.data.remote.datasource.MoviesRemoteDataSource
import com.machioni.libermovies.data.repository.MoviesRepository
import com.machioni.libermovies.domain.repositoryinterface.MoviesRepositoryInterface
import com.machioni.libermovies.presentation.scene.moviedetail.MovieDetailPresenter
import com.machioni.libermovies.presentation.scene.moviedetail.MovieDetailView
import com.machioni.libermovies.presentation.scene.moviedetail.MovieDetailUI
import com.machioni.libermovies.presentation.scene.movielist.MovieListPresenter
import com.machioni.libermovies.presentation.scene.movielist.MovieListView
import com.machioni.libermovies.presentation.scene.movielist.MovieListUI
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val context: Context) {
    @Provides
    @Singleton
    @ApplicationContext
    fun context() = context

    @Provides
    @Singleton
    fun retrofit() = Retrofit.Builder()
            .baseUrl("http://www.omdbapi.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

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

    @Provides
    @Singleton
    fun moviesRepository(moviesRepository: MoviesRepository) : MoviesRepositoryInterface {
        return moviesRepository
    }

    @Provides
    @Singleton
    fun moviesRemoteDataSource(retrofit: Retrofit) = retrofit.create(MoviesRemoteDataSource::class.java)
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

    fun inject(movieDetailFragment: MovieDetailPresenter)
    fun inject(movieListPresenter: MovieListPresenter)
}