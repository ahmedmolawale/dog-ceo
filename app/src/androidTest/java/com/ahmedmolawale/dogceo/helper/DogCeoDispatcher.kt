package com.ahmedmolawale.dogceo.helper

import com.ahmedmolawale.dogceo.sampledata.dogBreedImages
import com.ahmedmolawale.dogceo.sampledata.dogBreeds
import com.ahmedmolawale.dogceo.sampledata.emptyDogBreedImages
import com.ahmedmolawale.dogceo.sampledata.emptyDogBreeds
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class DogCeoDispatcher {

    /**
     * Return ok response from mock server
     */
    internal inner class RequestDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                BREEDS_URL -> MockResponse().setResponseCode(200)
                    .setBody(dogBreeds)
                BREED_IMAGES_URL -> MockResponse().setResponseCode(200)
                    .setBody(dogBreedImages)
                SUB_BREED_IMAGES_URL -> MockResponse().setResponseCode(200)
                    .setBody(dogBreedImages)
                else -> MockResponse().setResponseCode(400)
            }
        }
    }

    /**
     * Return ok response from mock server with empty data
     */
    internal inner class EmptyResponseRequestDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                BREEDS_URL -> MockResponse().setResponseCode(200)
                    .setBody(emptyDogBreeds)
                BREED_IMAGES_URL -> MockResponse().setResponseCode(200)
                    .setBody(emptyDogBreedImages)
                SUB_BREED_IMAGES_URL -> MockResponse().setResponseCode(200)
                    .setBody(emptyDogBreedImages)
                else -> MockResponse().setResponseCode(400)
            }
        }
    }

    /**
     * Return server error response from mock server
     */
    internal inner class ErrorRequestDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest) =
            MockResponse().setResponseCode(500)
    }
}
