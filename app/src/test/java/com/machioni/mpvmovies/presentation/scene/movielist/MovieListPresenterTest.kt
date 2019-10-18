package com.machioni.mpvmovies.presentation.scene.movielist

import androidx.lifecycle.Lifecycle
import com.machioni.mpvmovies.common.MOVIE_PAGE_SIZE
import com.machioni.mpvmovies.common.MyApplication
import com.machioni.mpvmovies.domain.model.Movie
import com.machioni.mpvmovies.domain.model.Page
import com.machioni.mpvmovies.domain.usecase.GetMovies
import io.mockk.*
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.terrakok.cicerone.Router
import java.util.concurrent.TimeUnit

internal class MovieListPresenterTest{
    private val fragment by lazy { spyk<MovieListPresenter>(recordPrivateCalls = true) }
    private val view = mockk<MovieListView>(relaxed = true)
    private val getMovies = mockk<GetMovies>(relaxed = true)
    private val router = mockk<Router>(relaxed = true)
    private val mockSearchChangesSubject = BehaviorSubject.create<String>()
    private val mockListEndReachedSubject = PublishSubject.create<Long>()
    private val delayScheduler = TestScheduler()

    @BeforeEach
    fun setup(){
        mockkObject(MyApplication.Companion)
        every { MyApplication.daggerComponent } returns mockk(relaxed = true)
        fragment.view = view
        fragment.getMovies = getMovies
        fragment.delayScheduler = delayScheduler
        every { fragment.router } returns router

        every { view.searchChangesSubject } returns mockSearchChangesSubject
        every { view.listEndReachedObservable } returns mockListEndReachedSubject
    }

    @Test
    fun `when a search query bigger than 2 letters is typed, calls getMovies use case`(){
        //given
        every { getMovies.getSingle(any()) } returns Single.just(Page(listOf(Movie("1","2","3","4", false)), 1))

        //when
        fragment.lifecycleSubject.onNext(Lifecycle.Event.ON_RESUME)
        fragment.onViewCreated(mockk(relaxed = true), null)
        mockSearchChangesSubject.onNext("har")
        delayScheduler.advanceTimeBy(900, TimeUnit.MILLISECONDS)

        //then
        verify {
            view.displayLoading()
            getMovies.getSingle(match { it.searchQuery == "har" })
            view.displayMovies(any())
        }
    }

    @Test
    fun `when a search query smaller than 3 letters is typed, does not call getMovies use case`(){
        //given
        every { getMovies.getSingle(any()) } returns Single.just(Page(listOf(Movie("1","2","3","4", false)), 10))

        //when
        fragment.lifecycleSubject.onNext(Lifecycle.Event.ON_RESUME)
        fragment.onViewCreated(mockk(relaxed = true), null)
        delayScheduler.advanceTimeBy(900, TimeUnit.MILLISECONDS)

        //then
        verify(inverse = true) {
            view.displayLoading()
            getMovies.getSingle(any())
            view.displayMovies(any())
        }
    }

    @Test
    fun `when the end of the list is reached, calls getMovies use case to get the next page if there are more results`(){
        //given
        every { getMovies.getSingle(any()) } returns Single.just(Page(List(10) { Movie("1","2","3","4", false) }, 20))

        //when
        fragment.lifecycleSubject.onNext(Lifecycle.Event.ON_RESUME)
        fragment.onViewCreated(mockk(relaxed = true), null)
        mockSearchChangesSubject.onNext("har")
        delayScheduler.advanceTimeBy(900, TimeUnit.MILLISECONDS)
        mockListEndReachedSubject.onNext(MOVIE_PAGE_SIZE)

        //then
        verify {
            view.displayLoading()
            getMovies.getSingle(match { it.page == 2L })
            view.displayMovies(any())
        }
    }

    @Test
    fun `when the end of the list is reached, does not calls getMovies use case to get the next page if there are no more results`(){
        //given
        every { getMovies.getSingle(any()) } returns Single.just(Page(listOf(Movie("1","2","3","4", false)), 1))

        //when
        fragment.lifecycleSubject.onNext(Lifecycle.Event.ON_RESUME)
        fragment.onViewCreated(mockk(relaxed = true), null)
        mockSearchChangesSubject.onNext("har")
        delayScheduler.advanceTimeBy(900, TimeUnit.MILLISECONDS)
        mockListEndReachedSubject.onNext(MOVIE_PAGE_SIZE)

        //then
        verify(inverse = true) {
            view.displayLoading()
            getMovies.getSingle(match { it.page == 2L })
            view.displayMovies(any())
        }
    }

    @Test
    fun `when an item is clicked on the view, it displays a toast and navigates to details of the item`(){
        //given
        val id = "1"
        every { view.itemClicksObservable } returns Observable.just(id)
        every { router.navigateTo(any()) } just Runs

        //when
        fragment.lifecycleSubject.onNext(Lifecycle.Event.ON_RESUME)
        fragment.onViewCreated(mockk(relaxed = true), null)

        //then
        verify {
            router.navigateTo(any())
        }
    }
}