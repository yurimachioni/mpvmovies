package com.machioni.mpvsample.presentation.scene.somethingdetail

import com.evernote.android.state.State
import com.machioni.mpvsample.common.MyApplication
import com.machioni.mpvsample.domain.usecase.GetSomethingById
import com.machioni.mpvsample.presentation.common.BackButtonListener
import com.machioni.mpvsample.presentation.common.BaseFragment
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class SomethingDetailFragment : BaseFragment(), BackButtonListener {

    @Inject
    override lateinit var view: SomethingDetailView

    @Inject
    lateinit var getSomethingById: GetSomethingById

    @State
    var somethingId = 1

    init{
        MyApplication.daggerComponent.inject(this)
    }

    companion object {
        fun newInstance(id: Int): SomethingDetailFragment = SomethingDetailFragment().apply{ somethingId = id }
    }

    override fun onFirstLoad() {
        getSomethingById()
    }

    private fun getSomethingById(){
        view.displayLoading()
        getSomethingById.getSingle(somethingId)
                .map { it.toViewModel() }
                .delayUntilActive()
                .subscribe({
                    view.displaySomething(it)
                }, {

                }).addTo(disposables)
    }
}