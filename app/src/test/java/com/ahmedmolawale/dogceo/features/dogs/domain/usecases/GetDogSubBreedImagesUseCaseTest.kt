package com.ahmedmolawale.dogceo.features.dogs.domain.usecases

import com.ahmedmolawale.dogceo.UnitTest
import com.ahmedmolawale.dogceo.core.functional.Result
import com.ahmedmolawale.dogceo.features.dogs.domain.model.DogBreedImage
import com.ahmedmolawale.dogceo.features.dogs.domain.repository.IDogBreedImagesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetDogSubBreedImagesUseCaseTest : UnitTest() {
    private lateinit var getDogSubBreedImagesUseCase: GetDogSubBreedImagesUseCase

    @MockK
    private lateinit var dogBreedImagesRepository: IDogBreedImagesRepository

    @Before
    fun setUp() {
        getDogSubBreedImagesUseCase = GetDogSubBreedImagesUseCase(dogBreedImagesRepository)
    }

    @Test
    fun `should call getDogBreeds from repository`() = runBlockingTest {
        val dogSubBreedParams = GetDogSubBreedImagesUseCase.DogSubBreedParams("hound", "afgan")
        coEvery {
            dogBreedImagesRepository.getDogSubBreedImages(
                dogSubBreedParams.breedName,
                dogSubBreedParams.subBreedName
            )
        } returns flow {
            emit(
                Result.Success(emptyList<DogBreedImage>())
            )
        }
        getDogSubBreedImagesUseCase.run(dogSubBreedParams)
        coVerify(exactly = 1) {
            dogBreedImagesRepository.getDogSubBreedImages(
                dogSubBreedParams.breedName,
                dogSubBreedParams.subBreedName
            )
        }
    }
}