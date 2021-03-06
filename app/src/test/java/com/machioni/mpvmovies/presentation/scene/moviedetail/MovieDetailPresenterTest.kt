package com.machioni.mpvmovies.presentation.scene.moviedetail

import androidx.lifecycle.Lifecycle
import com.machioni.mpvmovies.common.MyApplication
import com.machioni.mpvmovies.domain.model.DetailedMovie
import com.machioni.mpvmovies.domain.usecase.GetMovieById
import io.mockk.*
import io.reactivex.Single
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.terrakok.cicerone.Router

internal class MovieDetailPresenterTest{
    private val id = "1"
    private val presenter by lazy { spyk(MovieDetailPresenter.newInstance(id), recordPrivateCalls = true) }
    private val view = mockk<MovieDetailView>(relaxed = true)
    private val getMovieById = mockk<GetMovieById>(relaxed = true)
    private val router = mockk<Router>(relaxed = true)

    @BeforeEach
    fun setup(){
        mockkObject(MyApplication.Companion)
        every { MyApplication.daggerComponent } returns mockk(relaxed = true)
        presenter.view = view
        presenter.getMovieById = getMovieById
        every { presenter.router } returns router
        presenter.movieId = id
    }

    @Test
    fun `on first load, the passed movie is retrieved by id and the view is called to display it`(){
        //given
        every { getMovieById.getSingle(any()) } returns Single.just(DetailedMovie("","",id,"","","","","",false))

        //when
        presenter.lifecycleSubject.onNext(Lifecycle.Event.ON_RESUME)
        presenter.onViewCreated(mockk(relaxed = true), null)

        //then
        verify {
            view.displayLoading()
            getMovieById.getSingle(id)
            view.displayMovie(any())
        }
    }

    @Test
    fun `the content is not refreshed when the view is recreated`(){
        //given
        every { getMovieById.getSingle(any()) } returns Single.just(DetailedMovie("","",id,"","","","","",false))

        //when
        presenter.lifecycleSubject.onNext(Lifecycle.Event.ON_RESUME)
        presenter.onViewCreated(mockk(relaxed = true), null)
        presenter.onViewCreated(mockk(relaxed = true), mockk())

        //then
        verify(exactly = 1) {
            view.displayMovie(any())
        }
    }
}