package com.machioni.mpvsample.presentation.common

import androidx.fragment.app.Fragment
import com.machioni.mpvsample.presentation.scene.somethingdetail.SomethingDetailFragment
import com.machioni.mpvsample.presentation.scene.somethinglist.SomethingListFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

sealed class Screen : SupportAppScreen()

class SomethingListScreen : Screen() {
    override fun getFragment(): Fragment = SomethingListFragment.newInstance()
}

class SomethingDetailScreen(private val somethingId: Int) : Screen() {
    override fun getFragment(): Fragment = SomethingDetailFragment.newInstance(somethingId)
}