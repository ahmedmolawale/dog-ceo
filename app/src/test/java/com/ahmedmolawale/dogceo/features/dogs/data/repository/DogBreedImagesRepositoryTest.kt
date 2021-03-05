package com.ahmedmolawale.dogceo.features.dogs.data.repository

import com.ahmedmolawale.dogceo.UnitTest
import com.ahmedmolawale.dogceo.core.exception.Failure
import com.ahmedmolawale.dogceo.core.functional.Result
import com.ahmedmolawale.dogceo.features.dogs.data.mappers.toDomain
import com.ahmedmolawale.dogceo.features.dogs.data.remote.api.DogCeoApi
import com.ahmedmolawale.dogceo.features.dogs.data.remote.model.DogBreedImageResponse
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
class DogBreedImagesRepositoryTest : UnitTest() {
    private lateinit var dogBreedImagesRepository: DogBreedImagesRepository
    private lateinit var breedName: String
    private lateinit var subBreedName: String

    @MockK
    private lateinit var apiService: DogCeoApi

    @MockK
    private lateinit var dogBreedImageApiResponse: Response<DogBreedImageResponse>

    @Before
    fun setUp() {
        breedName = "hound"
        subBreedName = "basset"
        dogBreedImagesRepository = DogBreedImagesRepository(apiService)
    }

    @Test
    fun `getDogBreedImages with null responseBody should return data error`() = runBlockingTest {
        every { dogBreedImageApiResponse.body() } returns null
        every { dogBreedImageApiResponse.isSuccessful } returns true
        coEvery { apiService.fetchDogBreedImages(breedName) } returns dogBreedImageApiResponse

        val dogBreeds = dogBreedImagesRepository.getDogBreedImages(breedName)
        dogBreeds.collect { a ->
            assertThat(a).isEqualTo(Result.Error(Failure.DataError))
        }
    }

    @Test
    fun `getDogBreedImages should return server error when response is not successful`() =
        runBlockingTest {
            every { dogBreedImageApiResponse.isSuccessful } returns false
            coEvery { apiService.fetchDogBreedImages(breedName) } returns dogBreedImageApiResponse

            val dogBreeds = dogBreedImagesRepository.getDogBreedImages(breedName)
            dogBreeds.collect { a ->
                assertThat(a).isEqualTo(Result.Error(Failure.ServerError))
            }
        }

    @Test
    fun `getDogBreedImages should return data error if status not success`() = runBlockingTest {
        val dogBreedImageResponse = DogBreedImageResponse(
            status = "failure",
            message =
            listOf()
        )

        every { dogBreedImageApiResponse.isSuccessful } returns true
        every { dogBreedImageApiResponse.body() } returns dogBreedImageResponse
        coEvery { apiService.fetchDogBreedImages(breedName) } returns dogBreedImageApiResponse

        val dogBreeds = dogBreedImagesRepository.getDogBreedImages(breedName)
        dogBreeds.collect { a ->
            assertThat(a).isEqualTo(Result.Error(Failure.DataError))
        }
    }

    @Test
    fun `getDogBreedImages should return dog breed images list`() = runBlockingTest {
        val dogBreedImageResponse = DogBreedImageResponse(
            status = "success",
            message =
            listOf(
                "https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg",
                "https://images.dog.ceo/breeds/hound-afghan/n02088094_1007.jpg"
            )
        )

        every { dogBreedImageApiResponse.isSuccessful } returns true
        every { dogBreedImageApiResponse.body() } returns dogBreedImageResponse
        coEvery { apiService.fetchDogBreedImages(breedName) } returns dogBreedImageApiResponse

        val dogBreeds = dogBreedImagesRepository.getDogBreedImages(breedName)
        dogBreeds.collect { a ->
            assertThat(a).isEqualTo(Result.Success(dogBreedImageResponse.toDomain()))
        }
    }

    // sub breeds
    @Test
    fun `getDogSubBreedImages with null responseBody should return data error`() = runBlockingTest {
        every { dogBreedImageApiResponse.body() } returns null
        every { dogBreedImageApiResponse.isSuccessful } returns true
        coEvery {
            apiService.fetchDogSubBreedImages(
                breedName,
                subBreedName
            )
        } returns dogBreedImageApiResponse

        val dogBreeds = dogBreedImagesRepository.getDogSubBreedImages(breedName, subBreedName)
        dogBreeds.collect { a ->
            assertThat(a).isEqualTo(Result.Error(Failure.DataError))
        }
    }

    @Test
    fun `getDogSubBreedImages should return server error when response is not successful`() =
        runBlockingTest {
            every { dogBreedImageApiResponse.isSuccessful } returns false
            coEvery {
                apiService.fetchDogSubBreedImages(
                    breedName,
                    subBreedName
                )
            } returns dogBreedImageApiResponse

            val dogBreeds = dogBreedImagesRepository.getDogSubBreedImages(breedName, subBreedName)
            dogBreeds.collect { a ->
                assertThat(a).isEqualTo(Result.Error(Failure.ServerError))
            }
        }

    @Test
    fun `getDogSubBreedImages should return data error if status not success`() = runBlockingTest {
        val dogBreedImageResponse = DogBreedImageResponse(
            status = "failure",
            message =
            listOf()
        )

        every { dogBreedImageApiResponse.isSuccessful } returns true
        every { dogBreedImageApiResponse.body() } returns dogBreedImageResponse
        coEvery {
            apiService.fetchDogSubBreedImages(
                breedName,
                subBreedName
            )
        } returns dogBreedImageApiResponse

        val dogBreeds = dogBreedImagesRepository.getDogSubBreedImages(breedName, subBreedName)
        dogBreeds.collect { a ->
            assertThat(a).isEqualTo(Result.Error(Failure.DataError))
        }
    }

    @Test
    fun `getDogSubBreedImages should return dog breed images list`() = runBlockingTest {
        val dogBreedImageResponse = DogBreedImageResponse(
            status = "success",
            message =
            listOf(
                "https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg",
                "https://images.dog.ceo/breeds/hound-afghan/n02088094_1007.jpg"
            )
        )

        every { dogBreedImageApiResponse.isSuccessful } returns true
        every { dogBreedImageApiResponse.body() } returns dogBreedImageResponse
        coEvery {
            apiService.fetchDogSubBreedImages(
                breedName,
                subBreedName
            )
        } returns dogBreedImageApiResponse

        val dogBreeds = dogBreedImagesRepository.getDogSubBreedImages(breedName, subBreedName)
        dogBreeds.collect { a ->
            assertThat(a).isEqualTo(Result.Success(dogBreedImageResponse.toDomain()))
        }
    }
}
