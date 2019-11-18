package com.machioni.mpvmovies.presentation.scene

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import com.machioni.mpvmovies.R
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Matchers.hasEntry

//from: https://medium.com/android-bits/espresso-robot-pattern-in-kotlin-fc820ce250f7
open class BaseTestRobot {

    fun fillEditText(resId: Int, text: String): ViewInteraction =
            onView(withId(resId)).perform(ViewActions.replaceText(text), ViewActions.closeSoftKeyboard())

    fun clickButton(resId: Int): ViewInteraction = onView((withId(resId))).perform(ViewActions.click())

    fun textView(resId: Int): ViewInteraction = onView(withId(resId))

    fun matchText(viewInteraction: ViewInteraction, text: String): ViewInteraction = viewInteraction
            .check(matches(withSubstring(text)))

    fun matchText(resId: Int, text: String): ViewInteraction = matchText(textView(resId), text)

    fun viewDisplayed(resId: Int) = onView(withId(resId)).check(matches(isDisplayed()))

    fun viewHidden(resId: Int) = onView(withId(resId)).check(matches(not(isDisplayed())))

    fun listItemDisplayed(recyclerId: Int, partialText: String){
        onView(withId(recyclerId))
                .check(matches(hasDescendant(withText(containsString(partialText)))))
    }

    fun clickListItem(listRes: Int, position: Int) {
        onData(anything())
                .inAdapterView(allOf(withId(listRes)))
                .atPosition(position).perform(ViewActions.click())
    }


}