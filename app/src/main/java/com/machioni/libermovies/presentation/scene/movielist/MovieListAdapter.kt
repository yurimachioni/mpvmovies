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
    private val listEndReachedSubject: PublishSubject<Long> = PublishSubject.create()

    fun itemClicks(): Observable<String> = itemClicksSubject
    fun favoriteClicks(): Observable<MovieVM> = favoriteClicksSubject
    fun listEndReached(): Observable<Long> = listEndReachedSubject

    var movieList: List<MovieVM> = emptyList()

    fun setMovies(movies: List<MovieVM>) {
        movieList = movies
        clear()
        addAll(movies.map { MovieItem(it)})
    }

    fun addMovies(movies: List<MovieVM>){
        movieList = movieList + movies
        addAll(movies.map { MovieItem(it) })
    }

    fun updateMovie(movieVM: MovieVM){
        movieList.indexOfFirst { it.imdbId == movieVM.imdbId }.also { position ->
            (getItem(position) as? MovieItem)?.movieVM = movieVM
            notifyItemChanged(position)
        }
    }

    inner class MovieItem(var movieVM: MovieVM) : Item() {
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
                if(position == movieList.size - 1)
                    listEndReachedSubject.onNext(movieList.size.toLong())
            }
        }

        override fun getLayout(): Int = R.layout.movie_item
    }
}