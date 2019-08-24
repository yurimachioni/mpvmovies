package com.machioni.mpvsample.presentation.scene.somethinglist

import com.evernote.android.state.State
import com.machioni.mpvsample.common.MyApplication
import com.machioni.mpvsample.domain.model.Something
import com.machioni.mpvsample.domain.usecase.GetSomethings
import com.machioni.mpvsample.presentation.common.BackButtonListener
import com.machioni.mpvsample.presentation.common.BaseFragment
import com.machioni.mpvsample.presentation.common.SomethingDetailScreen
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class SomethingListFragment : BaseFragment(), BackButtonListener {

    @Inject
    override lateinit var view: SomethingListView

    @Inject
    lateinit var getSomethings: GetSomethings

    @State
    var totalItemsViewed = 1

    init{
        MyApplication.daggerComponent.inject(this)
    }

    companion object {
        fun newInstance(): SomethingListFragment = SomethingListFragment()
    }

    override fun onFirstLoad() {
        bindToView()
        getSomethings()
    }

    private fun bindToView(){
        view.onItemClickedObservable.subscribe{ somethingId ->
            view.displayToast("total items viewed: ${totalItemsViewed++}")
            router.navigateTo(SomethingDetailScreen(somethingId))
        }.addTo(disposables)
    }

    private fun getSomethings(){
        view.displayLoading()
        getSomethings.getSingle(Unit)
                .map { it.map(Something::toViewModel) }
                .delayUntilActive()
                .subscribe({
                    view.displaySomethings(it)
                }, {
                    view.displayToast("Erro")
                }).addTo(disposables)
    }

}