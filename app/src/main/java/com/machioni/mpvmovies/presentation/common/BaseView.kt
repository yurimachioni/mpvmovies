package com.machioni.mpvmovies.presentation.common

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import kotlinx.android.extensions.LayoutContainer

interface BaseView : LayoutContainer {
    val layoutId: Int
    val context : Context

    fun inflate(context: Context, parent: ViewGroup?) : View?
    fun initViews()
    fun displayToast(text: String)
    fun displayToast(@StringRes text: Int)
}