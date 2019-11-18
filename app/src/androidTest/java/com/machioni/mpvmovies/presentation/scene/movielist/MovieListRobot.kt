package com.machioni.mpvmovies.presentation.scene.movielist

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.machioni.mpvmovies.R
import com.machioni.mpvmovies.presentation.scene.BaseTestRobot

fun movieList(func: MovieListRobot.() -> Unit) = MovieListRobot().apply { func() }

class MovieListRobot : BaseTestRobot(){

    fun setSearchQuery(query: String) : ViewInteraction {
        val viewInteraction = fillEditText(R.id.searchEditText, query)
        Thread.sleep(650) //necessary despite RxIdler because it doesn't support delay(). Maybe try injecting testScheduler to advance time, but wouldn't work with E2E tests
        return viewInteraction
    }

    fun listDisplayed() = viewDisplayed(R.id.movieRecycler)
    fun listHidden() = viewHidden(R.id.movieRecycler)

    fun errorDisplayed() = viewDisplayed(R.id.errorTextView)
    fun errorHidden() = viewHidden(R.id.errorTextView)
    fun errorText(text: String) = matchText(R.id.errorTextView, text)

    fun searchFieldDisplayed() = viewDisplayed(R.id.searchEditText)

    fun movieDisplayed(partialMovieName: String) {
        onView(withId(R.id.movieRecycler)).check(matches(isDisplayed()))
        listItemDisplayed(R.id.movieRecycler, partialMovieName)
    }
}