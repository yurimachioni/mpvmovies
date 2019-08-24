package com.machioni.mpvsample.presentation.scene.moviedetail

import com.machioni.mpvsample.domain.model.Movie

fun Movie.toViewModel() = MovieDetailVM(id, text, imgUrl)