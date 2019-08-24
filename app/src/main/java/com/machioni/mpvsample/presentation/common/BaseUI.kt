package com.machioni.mpvsample.presentation.common

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.*

abstract class BaseUI : BaseView {

    override val context: Context
        get() = containerView.context

    override lateinit var containerView: View

    override fun inflate(context: Context, parent: ViewGroup?) : View {
        clearFindViewByIdCache()
        containerView = LayoutInflater.from(context).inflate(layoutId, parent, false)
        return containerView
    }
}