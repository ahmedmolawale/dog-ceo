package com.ahmedmolawale.dogceo.features.dogs.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ahmedmolawale.dogceo.UnitTest
import com.ahmedmolawale.dogceo.core.exception.Failure
import com.ahmedmolawale.dogceo.core.functional.Result
import com.ahmedmolawale.dogceo.features.dogs.domain.model.DogBreed
import com.ahmedmolawale.dogceo.features.dogs.domain.model.DogSubBreed
import com.ahmedmolawale.dogceo.features.dogs.domain.usecases.GetDogBreedsUseCase
import com.ahmedmolawale.dogceo.features.dogs.presentation.mapper.toPresentation
import com.ahmedmolawale.dogceo.features.dogs.presentation.model.state.DogBreedState
import com.ahmedmolawale.dogceo.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DogBreedViewModelTest : UnitTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var getDogBreedsUseCase: GetDogBreedsUseCase

    private lateinit var dogBreedViewModel: DogBreedViewModel
    private lateinit var dogBreeds: List<DogBreed>

    @Before
    fun setup() {
        dogBreeds = listOf(
            DogBreed(
                name = "hound",
                subBreed = listOf(DogSubBreed("afd"))

            )
        )
        dogBreedViewModel =
            DogBreedViewModel(getDogBreedsUseCase)
    }

    @Test
    fun `fetchDogBreeds should show empty state when no dog breed is found`() {
        every { getDogBreedsUseCase(any(), any(), any()) }.answers {
            thirdArg<(Result<List<DogBreed>>) -> Unit>()(Result.Success(emptyList()))
        }
        dogBreedViewModel.fetchDogBreeds()

        val res = dogBreedViewModel.dogBreedState.getOrAwaitValueTest()
        assertThat(res).isEqualTo(DogBreedState.NoDogBreeds)
    }

    @Test
    fun `fetchDogBreeds should show data state when dog breed is retrieved`() {
        every { getDogBreedsUseCase(any(), any(), any()) }.answers {
            thirdArg<(Result<List<DogBreed>>) -> Unit>()(Result.Success(dogBreeds))
        }
        dogBreedViewModel.fetchDogBreeds()

        val res = dogBreedViewModel.dogBreedState.getOrAwaitValueTest()
        assertThat(res).isInstanceOf(DogBreedState.DogBreeds::class.java)
        assertThat((res as DogBreedState.DogBreeds).dogBreeds).isNotEmpty()
        assertThat(res.dogBreeds).isEqualTo(dogBreeds.map { it.toPresentation() })
    }

    @Test
    fun `fetchDogBreeds should show error state when error occurs`() {
        every { getDogBreedsUseCase(any(), any(), any()) }.answers {
            thirdArg<(Result<List<DogBreed>>) -> Unit>()(Result.Error(Failure.ServerError))
        }
        dogBreedViewModel.fetchDogBreeds()

        val res = dogBreedViewModel.dogBreedState.getOrAwaitValueTest()
        assertThat(res).isInstanceOf(DogBreedState.Error::class.java)
        assertThat((res as DogBreedState.Error).errorMessage).isNotEmpty()
    }
}