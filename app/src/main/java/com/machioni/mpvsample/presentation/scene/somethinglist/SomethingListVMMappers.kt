package com.machioni.mpvsample.presentation.scene.somethinglist

import com.machioni.mpvsample.domain.model.Something

fun Something.toViewModel() = SomethingListVM(id, text, imgUrl)