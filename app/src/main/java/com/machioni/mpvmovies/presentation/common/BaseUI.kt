package com.machioni.mpvmovies.presentation.common

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

    override fun displayToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun displayToast(text: Int) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}