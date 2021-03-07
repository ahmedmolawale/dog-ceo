package com.ahmedmolawale.dogceo.features.dogs.presentation.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.ahmedmolawale.dogceo.R
import com.ahmedmolawale.dogceo.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@HiltAndroidTest
@MediumTest
class DogBreedFragmentTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Test
    fun shouldShowHeaderTitle() {
        launchFragmentInHiltContainer<DogBreedFragment>()
        onView(withId(R.id.header_title)).check(matches(isDisplayed()))
        onView(withId(R.id.header_title)).check(matches(withText(R.string.dog_breeds_title)))
    }

    @Test
    fun shouldShowSearchBar() {
        launchFragmentInHiltContainer<DogBreedFragment>()
        onView(withId(R.id.search)).check(matches(isDisplayed()))
        onView(withId(R.id.search)).check(matches(withHint(R.string.search_hint)))
    }
}
