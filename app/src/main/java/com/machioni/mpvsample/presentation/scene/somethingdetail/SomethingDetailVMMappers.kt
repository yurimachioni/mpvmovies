package com.machioni.mpvsample.presentation.scene.somethingdetail

import com.machioni.mpvsample.domain.model.Something

fun Something.toViewModel() = SomethingDetailVM(id, text, imgUrl)