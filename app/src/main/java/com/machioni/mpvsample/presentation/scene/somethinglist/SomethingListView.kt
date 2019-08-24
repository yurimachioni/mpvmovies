package com.machioni.mpvsample.presentation.scene.somethinglist

import com.machioni.mpvsample.presentation.common.BaseView
import com.machioni.mpvsample.presentation.scene.somethingdetail.SomethingDetailVM
import io.reactivex.Observable

interface SomethingListView : BaseView {
    val onItemClickedObservable: Observable<Int>

    fun displaySomethings(list: List<SomethingListVM>)
    fun displayToast(text: String)
    fun displayLoading()
}