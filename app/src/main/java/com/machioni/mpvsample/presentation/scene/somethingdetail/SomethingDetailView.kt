package com.machioni.mpvsample.presentation.scene.somethingdetail

import com.machioni.mpvsample.presentation.common.BaseView

interface SomethingDetailView : BaseView {
    fun displaySomething(somethingDetail: SomethingDetailVM)
    fun displayLoading()
}