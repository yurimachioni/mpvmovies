package com.machioni.mpvsample.presentation.scene.somethinglist

import androidx.lifecycle.Lifecycle
import com.machioni.mpvsample.common.MyApplication
import com.machioni.mpvsample.common.di.ApplicationComponent
import com.machioni.mpvsample.domain.model.Something
import com.machioni.mpvsample.domain.usecase.GetSomethings
import io.mockk.*
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.terrakok.cicerone.Router

internal class SomethingListFragmentTest{
    private val fragment by lazy { spyk<SomethingListFragment>(recordPrivateCalls = true) }
    private val view = mockk<SomethingListView>(relaxed = true)
    private val getSomethings = mockk<GetSomethings>(relaxed = true)
    private val router = mockk<Router>(relaxed = true)

    @BeforeEach
    fun setup(){
        mockkObject(MyApplication.Companion)
        every { MyApplication.daggerComponent } returns mockk(relaxed = true)
        fragment.view = view
        fragment.getSomethings = getSomethings
        every { fragment.router } returns router
    }

    @Test
    fun `on first load, the list of somethings is retrieved and the view is called to display it`(){
        //given
        every { getSomethings.getSingle(any()) } returns Single.just(listOf(Something(1)))

        //when
        fragment.lifecycleSubject.onNext(Lifecycle.Event.ON_RESUME)
        fragment.onViewCreated(mockk(relaxed = true), null)

        //then
        verifyOrder {
            view.displayLoading()
            getSomethings.getSingle(any())
            view.displaySomethings(any())
        }
    }

    @Test
    fun `the list is not refreshed when the view is recreated`(){
        //given
        every { getSomethings.getSingle(any()) } returns Single.just(listOf(Something(1)))

        //when
        fragment.lifecycleSubject.onNext(Lifecycle.Event.ON_RESUME)
        fragment.onViewCreated(mockk(relaxed = true), null)
        fragment.onViewCreated(mockk(relaxed = true), mockk())

        //then
        verify(exactly = 1) {
            view.displayLoading()
        }
    }

    @Test
    fun `when an item is clicked on the view, it displays a toast and navigates to details of the item`(){
        //given
        val id = 1
        every { view.onItemClickedObservable } returns Observable.just(id)
        every { router.navigateTo(any()) } just Runs

        //when
        fragment.lifecycleSubject.onNext(Lifecycle.Event.ON_RESUME)
        fragment.onViewCreated(mockk(relaxed = true), null)

        //then
        verifyOrder {
            view.displayToast(any())
            router.navigateTo(any())
        }
    }
}