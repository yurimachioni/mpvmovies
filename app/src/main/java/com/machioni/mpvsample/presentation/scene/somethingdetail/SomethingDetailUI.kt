package com.machioni.mpvsample.presentation.scene.somethingdetail

import android.view.View
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.machioni.mpvsample.R
import com.machioni.mpvsample.presentation.common.BaseUI
import kotlinx.android.synthetic.main.fragment_something_detail.*
import kotlinx.android.synthetic.main.loading.*
import javax.inject.Inject

class SomethingDetailUI @Inject constructor() : BaseUI(), SomethingDetailView {

    @LayoutRes
    override val layoutId = R.layout.fragment_something_detail

    override fun initViews() {

    }

    override fun displaySomething(somethingDetail: SomethingDetailVM){
        loadingLayout?.visibility = View.GONE
        Glide.with(context).load(somethingDetail.imgUrl).into(somethingImageView)
        somethingTextView.text = somethingDetail.text
    }

    override fun displayLoading() {
        loadingLayout?.visibility = View.VISIBLE
    }
}