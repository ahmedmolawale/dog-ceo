package com.ahmedmolawale.dogceo.features.dogs.presentation.view

import androidx.core.os.bundleOf
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.ahmedmolawale.dogceo.BaseTest
import com.ahmedmolawale.dogceo.R
import com.ahmedmolawale.dogceo.core.di.NetworkUrlModule
import com.ahmedmolawale.dogceo.helper.DogCeoDispatcher
import com.ahmedmolawale.dogceo.launchFragmentInHiltContainer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import java.util.*
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@HiltAndroidTest
@MediumTest
@UninstallModules(NetworkUrlModule::class)
class DogBreedImageFragmentTest : BaseTest() {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Module
    @InstallIn(SingletonComponent::class)
    class NetworkUrlModule {
        @Provides
        @Singleton
        fun provideUrl(): String = "http://localhost:8080/api/"
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

    @Test
    fun shouldShowRecyclerView() {
        val args = bundleOf(
            DogBreedFragment.BREED_NAME to breedName,
            DogBreedFragment.SUB_BREED_NAME to subbreedName
        )
        mockWebServer.dispatcher = DogCeoDispatcher().RequestDispatcher()
        launchFragmentInHiltContainer<DogBreedImageFragment>(fragmentArgs = args)
        onView(withId(R.id.dog_images)).check(matches(isDisplayed()))
        onView(withId(R.id.error_on_dog_breed_images)).check(matches(not(isDisplayed())))
        onView(withId(R.id.no_dog_breed_images)).check(matches(not(isDisplayed())))
    }

    @Test
    fun shouldShowErrorMessageView() {
        val args = bundleOf(
            DogBreedFragment.BREED_NAME to breedName,
            DogBreedFragment.SUB_BREED_NAME to subbreedName
        )
        mockWebServer.dispatcher = DogCeoDispatcher().ErrorRequestDispatcher()
        launchFragmentInHiltContainer<DogBreedImageFragment>(fragmentArgs = args)
        onView(withId(R.id.error_on_dog_breed_images)).check(matches(isDisplayed()))
        onView(withId(R.id.error_on_dog_breed_images)).check(matches(withText(R.string.errorMessage)))
        onView(withId(R.id.dog_images)).check(matches(not(isDisplayed())))
        onView(withId(R.id.no_dog_breed_images)).check(matches(not(isDisplayed())))
    }

    @Test
    fun shouldShowEmptyMessageView() {
        val args = bundleOf(
            DogBreedFragment.BREED_NAME to breedName,
            DogBreedFragment.SUB_BREED_NAME to subbreedName
        )
        mockWebServer.dispatcher = DogCeoDispatcher().EmptyResponseRequestDispatcher()
        launchFragmentInHiltContainer<DogBreedImageFragment>(fragmentArgs = args)
        onView(withId(R.id.no_dog_breed_images)).check(matches(isDisplayed()))
        onView(withId(R.id.no_dog_breed_images)).check(matches(withText(R.string.no_dog_breed_images)))
        onView(withId(R.id.error_on_dog_breed_images)).check(matches(not(isDisplayed())))
        onView(withId(R.id.dog_images)).check(matches(not(isDisplayed())))
    }
}
