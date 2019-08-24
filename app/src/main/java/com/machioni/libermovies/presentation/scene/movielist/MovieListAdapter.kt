package com.machioni.libermovies.presentation.scene.movielist

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.machioni.libermovies.R
import com.machioni.libermovies.presentation.common.GlideApp
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.movie_item.*

class MovieListAdapter : GroupAdapter<ViewHolder>(){

    private val onItemClickSubject: PublishSubject<String> = PublishSubject.create()
    fun onItemClick(): Observable<String> = onItemClickSubject

    fun setData(items: List<MovieVM>) {
        clear()

        items.forEach {
            add(MovieItem(it))
        }
    }

    inner class MovieItem(private val movieVM: MovieVM) : Item() {
        override fun bind(viewHolder: ViewHolder, position: Int) {
            with(viewHolder) {
                GlideApp.with(viewHolder.containerView.context)
                        .load(movieVM.posterUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.error)
                        .into(posterImg)
                titleText.text = movieVM.title
                yearText.text = movieVM.year
                itemParentView.setOnClickListener {
                    onItemClickSubject.onNext(movieVM.imdbId)
                }
            }
        }

        override fun getLayout(): Int = R.layout.movie_item
    }
}