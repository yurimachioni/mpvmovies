package com.machioni.mpvsample.presentation.scene.somethingdetail

import androidx.lifecycle.Lifecycle
import com.machioni.mpvsample.common.MyApplication
import com.machioni.mpvsample.domain.model.Something
import com.machioni.mpvsample.domain.usecase.GetSomethingById
import io.mockk.*
import io.reactivex.Single
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.terrakok.cicerone.Router

internal class SomethingDetailFragmentTest{
    private val id = 1
    private val fragment by lazy { spyk(SomethingDetailFragment.newInstance(id), recordPrivateCalls = true) }
    private val view = mockk<SomethingDetailView>(relaxed = true)
    private val getSomethingById = mockk<GetSomethingById>(relaxed = true)
    private val router = mockk<Router>(relaxed = true)

    @BeforeEach
    fun setup(){
        mockkObject(MyApplication.Companion)
        every { MyApplication.daggerComponent } returns mockk(relaxed = true)
        fragment.view = view
        fragment.getSomethingById = getSomethingById
        every { fragment.router } returns router
        fragment.somethingId = id
    }

    @Test
    fun `on first load, the passed something is retrieved by id and the view is called to display it`(){
        //given
        every { getSomethingById.getSingle(any()) } returns Single.just(Something(id))

        //when
        fragment.lifecycleSubject.onNext(Lifecycle.Event.ON_RESUME)
        fragment.onViewCreated(mockk(relaxed = true), null)

        //then
        verifyOrder {
            view.displayLoading()
            getSomethingById.getSingle(id)
            view.displaySomething(any())
        }
    }

    @Test
    fun `the content is not refreshed when the view is recreated`(){
        //given
        every { getSomethingById.getSingle(any()) } returns Single.just(Something(1))

        //when
        fragment.lifecycleSubject.onNext(Lifecycle.Event.ON_RESUME)
        fragment.onViewCreated(mockk(relaxed = true), null)
        fragment.onViewCreated(mockk(relaxed = true), mockk())

        //then
        verify(exactly = 1) {
            view.displaySomething(any())
        }
    }
}