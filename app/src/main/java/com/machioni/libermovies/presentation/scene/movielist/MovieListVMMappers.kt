package com.machioni.libermovies.presentation.scene.movielist

import com.machioni.libermovies.domain.model.Movie

fun Movie.toViewModel() = MovieListVM(id, text, imgUrl)