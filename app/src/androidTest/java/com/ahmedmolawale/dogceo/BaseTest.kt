package com.ahmedmolawale.dogceo

import androidx.test.espresso.IdlingRegistry
import com.ahmedmolawale.dogceo.core.idlingresource.EspressoIdlingResource
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before

open class BaseTest {

    lateinit var mockWebServer: MockWebServer
    lateinit var breedName: String
    lateinit var subbreedName: String

    @Before
    open fun setup() {
        breedName = "hound"
        subbreedName = "afgan"
        mockWebServer = MockWebServer().apply {
            start(8080)
        }
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    open fun tearDown() {
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }
}
