package com.ahmedmolawale.dogceo.features.dogs.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmedmolawale.dogceo.core.exception.Failure
import com.ahmedmolawale.dogceo.core.functional.Result
import com.ahmedmolawale.dogceo.features.dogs.domain.model.DogBreedImage
import com.ahmedmolawale.dogceo.features.dogs.domain.usecases.GetDogBreedImagesUseCase
import com.ahmedmolawale.dogceo.features.dogs.domain.usecases.GetDogSubBreedImagesUseCase
import com.ahmedmolawale.dogceo.features.dogs.presentation.mapper.toPresentation
import com.ahmedmolawale.dogceo.features.dogs.presentation.model.state.DogBreedImagesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DogBreedImagesViewModel @Inject constructor(
    private val getDogBreedImages: GetDogBreedImagesUseCase,
    private val getDogSubBreedImages: GetDogSubBreedImagesUseCase
) : ViewModel() {

    private val job = Job()

    private val _dogBreedImagesState = MutableLiveData<DogBreedImagesState>()
    val dogBreedImagesState: LiveData<DogBreedImagesState>
        get() = _dogBreedImagesState

    fun fetchDogBreedImages(breedName: String) {
        _dogBreedImagesState.value = DogBreedImagesState.Loading
        getDogBreedImages(job, breedName) {
            when (it) {
                is Result.Success -> {
                    handleSuccess(it.data)
                }
                is Result.Error -> {
                    handleError(it.failure)
                }
            }
        }
    }

    fun fetchDogSubBreedImages(breedName: String, subBreedName: String) {
        _dogBreedImagesState.value = DogBreedImagesState.Loading
        getDogSubBreedImages(
            job,
            GetDogSubBreedImagesUseCase.DogSubBreedParams(breedName, subBreedName)
        ) {
            when (it) {
                is Result.Success -> {
                    handleSuccess(it.data)
                }
                is Result.Error -> {
                    handleError(it.failure)
                }
            }
        }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun handleError(failure: Failure) {
        _dogBreedImagesState.value = DogBreedImagesState.Error("Unable to fetch data")
    }

    private fun handleSuccess(dogBreedImages: List<DogBreedImage>) {
        if (dogBreedImages.isEmpty()) {
            _dogBreedImagesState.value = DogBreedImagesState.NoDogBreedImages
        } else {
            _dogBreedImagesState.value =
                DogBreedImagesState.DogBreedImages(dogBreedImages.map { it.toPresentation() })
        }
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}
