package com.machioni.libermovies.presentation.scene.moviedetail

import com.machioni.libermovies.domain.model.Movie

fun Movie.toViewModel() = MovieDetailVM(id, text, imgUrl)