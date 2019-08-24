package com.machioni.mpvsample.presentation.scene.somethinglist

import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import com.machioni.mpvsample.R
import com.machioni.mpvsample.presentation.common.BaseUI
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_something_list.*
import kotlinx.android.synthetic.main.loading.*
import javax.inject.Inject

class SomethingListUI @Inject constructor() : BaseUI(), SomethingListView {

    @LayoutRes override val layoutId = R.layout.fragment_something_list

    private val adapter = SomethingListAdapter()

    override val onItemClickedObservable: Observable<Int> = adapter.onItemClick()

    override fun initViews(){
        somethingRecycler.adapter = adapter
        somethingRecycler.layoutManager = LinearLayoutManager(context)
    }

    override fun displaySomethings(list: List<SomethingListVM>){
        loadingLayout.visibility = View.GONE
        adapter.setData(list)
    }

    override fun displayToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun displayLoading() {
        loadingLayout.visibility = View.VISIBLE
    }
}