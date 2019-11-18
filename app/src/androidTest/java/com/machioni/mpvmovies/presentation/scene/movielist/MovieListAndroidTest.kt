package com.machioni.mpvmovies.presentation.scene.movielist

import androidx.fragment.app.testing.FragmentScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.squareup.rx2.idler.Rx2Idler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class MovieListAndroidTest{

    @Before
    fun setup(){
        RxJavaPlugins.setInitComputationSchedulerHandler(Rx2Idler.create("RxJava 2.x Computation Scheduler"))
        RxJavaPlugins.setInitIoSchedulerHandler(Rx2Idler.create("RxJava 2.x IO Scheduler"))
    }

    @Test
    fun displays_correct_views_when_started() {
        FragmentScenario.launchInContainer(MovieListPresenter::class.java)

        movieList {
            searchFieldDisplayed()
            listHidden()
            errorDisplayed()
            errorText("Type at least 3 characters to start searching.")
        }
    }

    @Test
    fun searches_movies_when_first_letters_are_typed_and_displays_them_when_response_arrives() {
        FragmentScenario.launchInContainer(MovieListPresenter::class.java)
        movieList {
            setSearchQuery("harry")
            listDisplayed()
            movieDisplayed("Potter")
        }
    }
}