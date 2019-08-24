package com.machioni.libermovies.presentation.scene.movielist

import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import com.machioni.libermovies.R
import com.machioni.libermovies.presentation.common.BaseUI
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.android.synthetic.main.loading.*
import javax.inject.Inject

class MovieListUI @Inject constructor() : BaseUI(), MovieListView {

    @LayoutRes override val layoutId = R.layout.fragment_movie_list

    private val adapter = MovieListAdapter()

    override val onItemClickedObservable: Observable<String> = adapter.onItemClick()

    override fun initViews(){
        movieRecycler.adapter = adapter
        movieRecycler.layoutManager = LinearLayoutManager(context)
    }

    override fun displayMovies(list: List<MovieVM>){
        loadingLayout.visibility = View.GONE
        adapter.setData(list)
    }

    override fun displayToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun displayLoading() {
        loadingLayout.visibility = View.VISIBLE
    }
}