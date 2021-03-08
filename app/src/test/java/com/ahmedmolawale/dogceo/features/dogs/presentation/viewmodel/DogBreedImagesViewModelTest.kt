package com.ahmedmolawale.dogceo.features.dogs.presentation.viewmodel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ahmedmolawale.dogceo.R
import com.ahmedmolawale.dogceo.UnitTest
import com.ahmedmolawale.dogceo.core.exception.Failure
import com.ahmedmolawale.dogceo.core.functional.Result
import com.ahmedmolawale.dogceo.features.dogs.domain.model.DogBreedImage
import com.ahmedmolawale.dogceo.features.dogs.domain.usecases.GetDogBreedImagesUseCase
import com.ahmedmolawale.dogceo.features.dogs.domain.usecases.GetDogSubBreedImagesUseCase
import com.ahmedmolawale.dogceo.features.dogs.presentation.mapper.toPresentation
import com.ahmedmolawale.dogceo.features.dogs.presentation.model.state.DogBreedImagesState
import com.ahmedmolawale.dogceo.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P], manifest = Config.NONE)
class DogBreedImagesViewModelTest : UnitTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var getDogBreedImages: GetDogBreedImagesUseCase

    @MockK
    private lateinit var getDogSubBreedImages: GetDogSubBreedImagesUseCase

    private lateinit var dogBreedImagesViewModel: DogBreedImagesViewModel
    private lateinit var dogBreedImages: List<DogBreedImage>
    private lateinit var breedName: String
    private lateinit var subBreedName: String
    private lateinit var dogSubBreedParams: GetDogSubBreedImagesUseCase.DogSubBreedParams

    @Before
    fun setup() {
        breedName = "hound"
        subBreedName = "afgan"
        dogSubBreedParams = GetDogSubBreedImagesUseCase.DogSubBreedParams(breedName, subBreedName)
        dogBreedImages = listOf(
            DogBreedImage(
                imageUrl = "https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg"
            )
        )
        dogBreedImagesViewModel =
            DogBreedImagesViewModel(getDogBreedImages, getDogSubBreedImages)
    }

    @Test
    fun `fetchDogBreedImages should show empty state when no dog breed images is found`() {
        every { getDogBreedImages(any(), breedName, any()) }.answers {
            thirdArg<(Result<List<DogBreedImage>>) -> Unit>()(Result.Success(emptyList()))
        }
        dogBreedImagesViewModel.fetchDogBreedImages(breedName)

        val res = dogBreedImagesViewModel.dogBreedImagesState.getOrAwaitValueTest()
        assertThat(res).isEqualTo(DogBreedImagesState.NoDogBreedImages)
    }

    @Test
    fun `fetchDogBreedImages should show error state when error occurs`() {
        every { getDogBreedImages(any(), breedName, any()) }.answers {
            thirdArg<(Result<List<DogBreedImage>>) -> Unit>()(Result.Error(Failure.ServerError))
        }
        dogBreedImagesViewModel.fetchDogBreedImages(breedName)

        val res = dogBreedImagesViewModel.dogBreedImagesState.getOrAwaitValueTest()
        assertThat(res).isInstanceOf(DogBreedImagesState.Error::class.java)
        assertThat((res as DogBreedImagesState.Error).errorMessageId).isEqualTo(R.string.errorMessage)
    }

    @Test
    fun `fetchDogBreedImages should show data state when dog breed images is retrieved`() {
        every { getDogBreedImages(any(), breedName, any()) }.answers {
            thirdArg<(Result<List<DogBreedImage>>) -> Unit>()(Result.Success(dogBreedImages))
        }
        dogBreedImagesViewModel.fetchDogBreedImages(breedName)

        val res = dogBreedImagesViewModel.dogBreedImagesState.getOrAwaitValueTest()
        assertThat(res).isInstanceOf(DogBreedImagesState.DogBreedImages::class.java)
        assertThat((res as DogBreedImagesState.DogBreedImages).dogBreedImages).isNotEmpty()
        assertThat(res.dogBreedImages).isEqualTo(dogBreedImages.map { it.toPresentation() })
    }

    @Test
    fun `fetchDogSubBreedImages should show empty state when no dog subbreed images is found`() {
        every { getDogSubBreedImages(any(), dogSubBreedParams, any()) }.answers {
            thirdArg<(Result<List<DogBreedImage>>) -> Unit>()(Result.Success(emptyList()))
        }
        dogBreedImagesViewModel.fetchDogSubBreedImages(breedName, subBreedName)
        val res = dogBreedImagesViewModel.dogBreedImagesState.getOrAwaitValueTest()
        assertThat(res).isEqualTo(DogBreedImagesState.NoDogBreedImages)
    }

    @Test
    fun `fetchDogSubBreedImages should show error state when error occurs`() {
        every { getDogSubBreedImages(any(), dogSubBreedParams, any()) }.answers {
            thirdArg<(Result<List<DogBreedImage>>) -> Unit>()(Result.Error(Failure.ServerError))
        }
        dogBreedImagesViewModel.fetchDogSubBreedImages(breedName, subBreedName)
        val res = dogBreedImagesViewModel.dogBreedImagesState.getOrAwaitValueTest()
        assertThat(res).isInstanceOf(DogBreedImagesState.Error::class.java)
        assertThat((res as DogBreedImagesState.Error).errorMessageId).isEqualTo(R.string.errorMessage)
    }

    @Test
    fun `fetchDogSubBreedImages should show data state when dog subbreed images is retrieved`() {
        every { getDogSubBreedImages(any(), dogSubBreedParams, any()) }.answers {
            thirdArg<(Result<List<DogBreedImage>>) -> Unit>()(Result.Success(dogBreedImages))
        }
        dogBreedImagesViewModel.fetchDogSubBreedImages(breedName, subBreedName)
        val res = dogBreedImagesViewModel.dogBreedImagesState.getOrAwaitValueTest()
        assertThat(res).isInstanceOf(DogBreedImagesState.DogBreedImages::class.java)
        assertThat((res as DogBreedImagesState.DogBreedImages).dogBreedImages).isNotEmpty()
        assertThat(res.dogBreedImages).isEqualTo(dogBreedImages.map { it.toPresentation() })
    }
}
