package com.machioni.mpvsample.presentation.scene.movielist

import com.bumptech.glide.Glide
import com.machioni.mpvsample.R
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.movie_item.*

class MovieListAdapter : GroupAdapter<ViewHolder>(){

    private val onItemClickSubject: PublishSubject<Int> = PublishSubject.create()
    fun onItemClick(): Observable<Int> = onItemClickSubject

    fun setData(items: List<MovieListVM>) {
        clear()

        items.forEach {
            add(MovieItem(it))
        }
    }

    inner class MovieItem(val movieListVM: MovieListVM) : Item() {
        override fun bind(viewHolder: ViewHolder, position: Int) {
            with(viewHolder) {
                Glide.with(viewHolder.containerView.context).load(movieListVM.imgUrl).into(movieImg)
                movieText.text = movieListVM.text
                itemParentView.setOnClickListener {
                    onItemClickSubject.onNext(movieListVM.id)
                }
            }
        }

        override fun getLayout(): Int = R.layout.movie_item
    }
}