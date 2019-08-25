package com.machioni.libermovies.presentation.scene.movielist

import android.view.View
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.widget.textChanges
import com.machioni.libermovies.R
import com.machioni.libermovies.presentation.common.BaseUI
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.android.synthetic.main.loading.*
import javax.inject.Inject

class MovieListUI @Inject constructor() : BaseUI(), MovieListView {

    @LayoutRes override val layoutId = R.layout.fragment_movie_list

    private val adapter = MovieListAdapter()

    override val itemClicksObservable: Observable<String> = adapter.itemClicks()
    override val searchChangesSubject: PublishSubject<String> = PublishSubject.create()
    override val favoriteClicksObservable: Observable<MovieVM> = adapter.favoriteClicks()

    override fun initViews(){
        movieRecycler.adapter = adapter
        movieRecycler.layoutManager = LinearLayoutManager(context)
        searchEditText.textChanges().map { it.toString() }.subscribe(searchChangesSubject)
    }

    override fun displayMovies(list: List<MovieVM>){
        errorTextView.visibility = View.GONE
        movieRecycler.visibility = View.VISIBLE
        adapter.movies = list
    }

    override fun updateMovie(movieVM: MovieVM) {
        adapter.updateMovie(movieVM)
    }

    override fun displayInstructions() {
        movieRecycler.visibility = View.GONE
        errorTextView.visibility = View.VISIBLE
        errorTextView.text = containerView.context.getString(R.string.type_to_search)
    }

    override fun displayEmptyState() {
        movieRecycler.visibility = View.GONE
        errorTextView.visibility = View.VISIBLE
        errorTextView.text = containerView.context.getString(R.string.not_found)
    }

    override fun displayError() {
        movieRecycler.visibility = View.GONE
        errorTextView.visibility = View.VISIBLE
        errorTextView.text = containerView.context.getString(R.string.error)
    }

    override fun displayLoading() {
        loadingLayout.visibility = View.VISIBLE
    }

    override fun dismissLoading() {
        loadingLayout.visibility = View.GONE
    }
}