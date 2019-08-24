package com.machioni.mpvsample.presentation.scene.somethinglist

import com.bumptech.glide.Glide
import com.machioni.mpvsample.R
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.something_item.*

class SomethingListAdapter : GroupAdapter<ViewHolder>(){

    private val onItemClickSubject: PublishSubject<Int> = PublishSubject.create()
    fun onItemClick(): Observable<Int> = onItemClickSubject

    fun setData(items: List<SomethingListVM>) {
        clear()

        items.forEach {
            add(SomethingItem(it))
        }
    }

    inner class SomethingItem(val somethingListVM: SomethingListVM) : Item() {
        override fun bind(viewHolder: ViewHolder, position: Int) {
            with(viewHolder) {
                Glide.with(viewHolder.containerView.context).load(somethingListVM.imgUrl).into(somethingImg)
                somethingText.text = somethingListVM.text
                itemParentView.setOnClickListener {
                    onItemClickSubject.onNext(somethingListVM.id)
                }
            }
        }

        override fun getLayout(): Int = R.layout.something_item
    }
}