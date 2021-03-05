package com.ahmedmolawale.dogceo.features.dogs.data.repository

import com.ahmedmolawale.dogceo.UnitTest
import com.ahmedmolawale.dogceo.core.exception.Failure
import com.ahmedmolawale.dogceo.core.functional.Result
import com.ahmedmolawale.dogceo.features.dogs.data.mappers.toDomain
import com.ahmedmolawale.dogceo.features.dogs.data.remote.api.DogCeoApi
import com.ahmedmolawale.dogceo.features.dogs.data.remote.model.DogBreedResponse
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class DogBreedsRepositoryTest : UnitTest() {

    private lateinit var dogBreedsRepository: DogBreedsRepository

    @MockK
    private lateinit var apiService: DogCeoApi

    @MockK
    private lateinit var dogBreedApiResponse: Response<DogBreedResponse>

    @Before
    fun setUp() {
        dogBreedsRepository = DogBreedsRepository(apiService)
    }

    @Test
    fun `getDogBreeds with null responseBody should return data error`() = runBlockingTest {
        every { dogBreedApiResponse.body() } returns null
        every { dogBreedApiResponse.isSuccessful } returns true
        coEvery { apiService.fetchDogBreeds() } returns dogBreedApiResponse

        val dogBreeds = dogBreedsRepository.getDogBreeds()
        dogBreeds.collect { a ->
            assertThat(a).isEqualTo(Result.Error(Failure.DataError))
        }
    }

    @Test
    fun `getDogBreeds should return server error when response is not successful`() =
        runBlockingTest {
            every { dogBreedApiResponse.isSuccessful } returns false
            coEvery { apiService.fetchDogBreeds() } returns dogBreedApiResponse

            val dogBreeds = dogBreedsRepository.getDogBreeds()
            dogBreeds.collect { a ->
                assertThat(a).isEqualTo(Result.Error(Failure.ServerError))
            }
        }

    @Test
    fun `getDogBreeds should return data error if status not success`() = runBlockingTest {
        val dogBreedResponse = DogBreedResponse(
            status = "failure",
            message =
            mapOf()
        )

        every { dogBreedApiResponse.isSuccessful } returns true
        every { dogBreedApiResponse.body() } returns dogBreedResponse
        coEvery { apiService.fetchDogBreeds() } returns dogBreedApiResponse

        val dogBreeds = dogBreedsRepository.getDogBreeds()
        dogBreeds.collect { a ->
            assertThat(a).isEqualTo(Result.Error(Failure.DataError))
        }
    }

    @Test
    fun `getDogBreeds should return dog breed list`() = runBlockingTest {
        val dogBreedResponse = DogBreedResponse(
            status = "success",
            message =
            mapOf(
                "hound" to listOf("afghan", "basset"),
                "australian" to listOf("shepherd")
            )
        )

        every { dogBreedApiResponse.isSuccessful } returns true
        every { dogBreedApiResponse.body() } returns dogBreedResponse
        coEvery { apiService.fetchDogBreeds() } returns dogBreedApiResponse

        val dogBreeds = dogBreedsRepository.getDogBreeds()
        dogBreeds.collect { a ->
            assertThat(a).isEqualTo(Result.Success(dogBreedResponse.toDomain()))
        }
    }
}
