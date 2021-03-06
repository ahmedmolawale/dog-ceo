package com.ahmedmolawale.dogceo.features.dogs.domain.usecases

import com.ahmedmolawale.dogceo.UnitTest
import com.ahmedmolawale.dogceo.core.functional.Result
import com.ahmedmolawale.dogceo.features.dogs.domain.model.DogBreed
import com.ahmedmolawale.dogceo.features.dogs.domain.repository.IDogBreedsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetDogBreedsUseCaseTest : UnitTest() {

    private lateinit var getDogBreedsUseCase: GetDogBreedsUseCase

    @MockK
    private lateinit var dogBreedsRepository: IDogBreedsRepository

    @Before
    fun setUp() {
        getDogBreedsUseCase = GetDogBreedsUseCase(dogBreedsRepository)
    }

    @Test
    fun `should call getDogBreeds from repository`() = runBlockingTest {
        coEvery { dogBreedsRepository.getDogBreeds() } returns flow {
            emit(
                Result.Success(emptyList<DogBreed>())
            )
        }
        getDogBreedsUseCase.run(GetDogBreedsUseCase.None())
        coVerify(exactly = 1) { dogBreedsRepository.getDogBreeds() }
    }
}
