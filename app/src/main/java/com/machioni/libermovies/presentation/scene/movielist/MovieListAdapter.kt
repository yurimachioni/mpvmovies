package com.machioni.libermovies.presentation.scene.movielist

import com.machioni.libermovies.R
import com.machioni.libermovies.presentation.common.GlideApp
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.movie_item.*

class MovieListAdapter : GroupAdapter<ViewHolder>(){

    private val itemClicksSubject: PublishSubject<String> = PublishSubject.create()
    private val favoriteClicksSubject: PublishSubject<MovieVM> = PublishSubject.create()

    fun itemClicks(): Observable<String> = itemClicksSubject
    fun favoriteClicks(): Observable<MovieVM> = favoriteClicksSubject

    var movies: List<MovieVM> = emptyList()
    set(value) {
        field = value
        clear()
        addAll(value.map { MovieItem(it)})
    }

    fun updateMovie(movieVM: MovieVM){
        movies.indexOfFirst { it.imdbId == movieVM.imdbId }.also {
            removeGroup(it)
            add(it, MovieItem(movieVM))
        }
    }

    inner class MovieItem(private val movieVM: MovieVM) : Item() {
        override fun bind(viewHolder: ViewHolder, position: Int) {
            with(viewHolder) {
                GlideApp.with(viewHolder.containerView.context)
                        .load(movieVM.posterUrl)
                        .fitCenter()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.error)
                        .into(posterImg)

                GlideApp.with(viewHolder.containerView.context)
                        .load(if(movieVM.isFavorite) R.drawable.favorite_on else R.drawable.favorite_off)
                        .into(favoriteImageView)

                titleText.text = movieVM.title
                yearText.text = movieVM.year
                itemParentView.setOnClickListener {
                    itemClicksSubject.onNext(movieVM.imdbId)
                }
                favoriteImageView.setOnClickListener {
                    favoriteClicksSubject.onNext(movieVM)
                }
            }
        }

        override fun getLayout(): Int = R.layout.movie_item
    }
}