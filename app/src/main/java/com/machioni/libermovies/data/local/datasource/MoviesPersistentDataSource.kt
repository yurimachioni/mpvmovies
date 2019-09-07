package com.machioni.libermovies.data.local.datasource

import com.pacoworks.rxpaper2.RxPaperBook
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesPersistentDataSource @Inject constructor(){
    val FAVORITES_KEY = "FAVORITES"

    fun addMovieToFavorites(id: String): Completable {
        return getFavoriteMovies()
                .flatMapCompletable {
                    RxPaperBook.with().write(FAVORITES_KEY, it.plus(id))
                }
    }

    fun removeMovieFromFavorites(id: String): Completable {
        return getFavoriteMovies()
                .flatMapCompletable {
                    RxPaperBook.with().write(FAVORITES_KEY, it.minus(id))
                }
    }

    fun getFavoriteMovies() : Single<Set<String>> {
        return RxPaperBook.with().read(FAVORITES_KEY, emptySet())
    }
}