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
class GetDogBreedImagesUseCaseTest : UnitTest() {
    private lateinit var getDogBreedImagesUseCase: GetDogBreedImagesUseCase

    @MockK
    private lateinit var dogBreedImagesRepository: IDogBreedImagesRepository

    @Before
    fun setUp() {
        getDogBreedImagesUseCase = GetDogBreedImagesUseCase(dogBreedImagesRepository)
    }

    @Test
    fun `should call getDogBreeds from repository`() = runBlockingTest {
        val breedName = "hound"
        coEvery { dogBreedImagesRepository.getDogBreedImages(breedName) } returns flow {
            emit(
                Result.Success(emptyList<DogBreedImage>())
            )
        }
        getDogBreedImagesUseCase.run(breedName)
        coVerify(exactly = 1) { dogBreedImagesRepository.getDogBreedImages(breedName) }
    }
}