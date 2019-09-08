package com.machioni.mpvmovies.presentation.scene.movielist

import android.view.View
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.widget.textChanges
import com.machioni.mpvmovies.R
import com.machioni.mpvmovies.presentation.common.BaseUI
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.android.synthetic.main.loading.*
import javax.inject.Inject

class MovieListUI @Inject constructor() : BaseUI(), MovieListView {

    @LayoutRes override val layoutId = R.layout.fragment_movie_list

    private val adapter = MovieListAdapter()

    override val itemClicksObservable: Observable<String> = adapter.itemClicks()
    override val searchChangesSubject: BehaviorSubject<String> = BehaviorSubject.create()
    override val favoriteClicksObservable: Observable<MovieVM> = adapter.favoriteClicks()
    override val listEndReachedObservable: Observable<Long> = adapter.listEndReached()

    override fun initViews(){
        movieRecycler.adapter = adapter
        movieRecycler.layoutManager = LinearLayoutManager(context)
        searchEditText.textChanges().map { it.toString() }.subscribe(searchChangesSubject)
    }

    override fun displayMovies(movies: List<MovieVM>){
        errorTextView.visibility = View.GONE
        movieRecycler.visibility = View.VISIBLE
        adapter.setMovies(movies)
    }

    override fun addMovies(movies: List<MovieVM>) {
        adapter.addMovies(movies)
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