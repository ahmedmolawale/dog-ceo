package com.ahmedmolawale.dogceo.features.dogs.presentation.view

import androidx.core.os.bundleOf
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.ahmedmolawale.dogceo.R
import com.ahmedmolawale.dogceo.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

@ExperimentalCoroutinesApi
@HiltAndroidTest
@MediumTest
class DogBreedImageFragmentTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    private lateinit var breedName: String
    private lateinit var subbreedName: String

    @Before
    fun setup() {
        breedName = "hound"
        subbreedName = "afgan"
    }

    @Test
    fun shouldShowHeaderAsDogBreedName() {
        val args = bundleOf(
            DogBreedFragment.BREED_NAME to breedName,
            DogBreedFragment.SUB_BREED_NAME to null
        )
        val expectedHeader = breedName.capitalize(Locale.getDefault())
        launchFragmentInHiltContainer<DogBreedImageFragment>(fragmentArgs = args)
        onView(withId(R.id.header_title))
            .check(matches(isDisplayed()))
        onView(withId(R.id.header_title))
            .check(matches(withText(expectedHeader)))
    }

    @Test
    fun shouldShowHeaderAsDogBreedAndSubBreedName() {
        val args = bundleOf(
            DogBreedFragment.BREED_NAME to breedName,
            DogBreedFragment.SUB_BREED_NAME to subbreedName
        )
        val expectedHeader = breedName.plus("-").plus(subbreedName).capitalize(Locale.getDefault())
        launchFragmentInHiltContainer<DogBreedImageFragment>(fragmentArgs = args)
        onView(withId(R.id.header_title))
            .check(matches(isDisplayed()))
        onView(withId(R.id.header_title))
            .check(matches(withText(expectedHeader)))
    }
}
