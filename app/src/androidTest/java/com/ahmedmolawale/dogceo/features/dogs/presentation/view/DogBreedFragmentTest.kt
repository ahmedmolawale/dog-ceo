package com.ahmedmolawale.dogceo.features.dogs.presentation.view

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
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@MediumTest
@HiltAndroidTest
@UninstallModules(NetworkUrlModule::class)
class DogBreedFragmentTest : BaseTest() {

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

    @Test
    fun shouldShowRecyclerView() {
        mockWebServer.dispatcher = DogCeoDispatcher().RequestDispatcher()
        launchFragmentInHiltContainer<DogBreedFragment>()
        onView(withId(R.id.dog_breed_recycler_view)).check(matches(isDisplayed()))
        onView(withId(R.id.error_on_dog_breeds)).check(matches(not(isDisplayed())))
        onView(withId(R.id.no_dog_breed)).check(matches(not(isDisplayed())))
    }

    @Test
    fun shouldShowErrorMessageView() {
        mockWebServer.dispatcher = DogCeoDispatcher().ErrorRequestDispatcher()
        launchFragmentInHiltContainer<DogBreedFragment>()
        onView(withId(R.id.error_on_dog_breeds)).check(matches(isDisplayed()))
        onView(withId(R.id.error_on_dog_breeds)).check(matches(withText(R.string.errorMessage)))
        onView(withId(R.id.dog_breed_recycler_view)).check(matches(not(isDisplayed())))
        onView(withId(R.id.no_dog_breed)).check(matches(not(isDisplayed())))
    }

    @Test
    fun shouldShowEmptyMessageView() {
        mockWebServer.dispatcher = DogCeoDispatcher().EmptyResponseRequestDispatcher()
        launchFragmentInHiltContainer<DogBreedFragment>()
        onView(withId(R.id.no_dog_breed)).check(matches(isDisplayed()))
        onView(withId(R.id.no_dog_breed)).check(matches(withText(R.string.no_dog_breeds)))
        onView(withId(R.id.error_on_dog_breeds)).check(matches(not(isDisplayed())))
        onView(withId(R.id.dog_breed_recycler_view)).check(matches(not(isDisplayed())))
    }
}
