package com.machioni.libermovies.presentation.scene.moviedetail

import androidx.lifecycle.Lifecycle
import com.machioni.libermovies.common.MyApplication
import com.machioni.libermovies.domain.model.Movie
import com.machioni.libermovies.domain.usecase.GetMovieById
import io.mockk.*
import io.reactivex.Single
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.terrakok.cicerone.Router

internal class MovieDetailFragmentTest{
    private val id = 1
    private val fragment by lazy { spyk(MovieDetailPresenter.newInstance(id), recordPrivateCalls = true) }
    private val view = mockk<MovieDetailView>(relaxed = true)
    private val getMovieById = mockk<GetMovieById>(relaxed = true)
    private val router = mockk<Router>(relaxed = true)

    @BeforeEach
    fun setup(){
        mockkObject(MyApplication.Companion)
        every { MyApplication.daggerComponent } returns mockk(relaxed = true)
        fragment.view = view
        fragment.getMovieById = getMovieById
        every { fragment.router } returns router
        fragment.movieId = id
    }

    @Test
    fun `on first load, the passed movie is retrieved by id and the view is called to display it`(){
        //given
        every { getMovieById.getSingle(any()) } returns Single.just(Movie(id))

        //when
        fragment.lifecycleSubject.onNext(Lifecycle.Event.ON_RESUME)
        fragment.onViewCreated(mockk(relaxed = true), null)

        //then
        verifyOrder {
            view.displayLoading()
            getMovieById.getSingle(id)
            view.displayMovie(any())
        }
    }

    @Test
    fun `the content is not refreshed when the view is recreated`(){
        //given
        every { getMovieById.getSingle(any()) } returns Single.just(Movie(1))

        //when
        fragment.lifecycleSubject.onNext(Lifecycle.Event.ON_RESUME)
        fragment.onViewCreated(mockk(relaxed = true), null)
        fragment.onViewCreated(mockk(relaxed = true), mockk())

        //then
        verify(exactly = 1) {
            view.displayMovie(any())
        }
    }
}