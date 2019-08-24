package com.machioni.libermovies.presentation.scene.movielist

import androidx.lifecycle.Lifecycle
import com.machioni.libermovies.common.MyApplication
import com.machioni.libermovies.domain.model.Movie
import com.machioni.libermovies.domain.usecase.GetMovies
import io.mockk.*
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.terrakok.cicerone.Router

internal class MovieListPresenterTest{
    private val fragment by lazy { spyk<MovieListPresenter>(recordPrivateCalls = true) }
    private val view = mockk<MovieListView>(relaxed = true)
    private val getMovies = mockk<GetMovies>(relaxed = true)
    private val router = mockk<Router>(relaxed = true)

    @BeforeEach
    fun setup(){
        mockkObject(MyApplication.Companion)
        every { MyApplication.daggerComponent } returns mockk(relaxed = true)
        fragment.view = view
        fragment.getMovies = getMovies
        every { fragment.router } returns router
    }

    @Test
    fun `on first load, the list of movies is retrieved and the view is called to display it`(){
        //given
        every { getMovies.getSingle(any()) } returns Single.just(listOf(Movie(1)))

        //when
        fragment.lifecycleSubject.onNext(Lifecycle.Event.ON_RESUME)
        fragment.onViewCreated(mockk(relaxed = true), null)

        //then
        verifyOrder {
            view.displayLoading()
            getMovies.getSingle(any())
            view.displayMovies(any())
        }
    }

    @Test
    fun `the list is not refreshed when the view is recreated`(){
        //given
        every { getMovies.getSingle(any()) } returns Single.just(listOf(Movie(1)))

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
        every { view.itemClicksObservable } returns Observable.just(id)
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