package com.machioni.mpvsample.presentation.scene.movielist

import com.machioni.mpvsample.domain.model.Movie

fun Movie.toViewModel() = MovieListVM(id, text, imgUrl)