package com.ahmedmolawale.dogceo.features.dogs.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmedmolawale.dogceo.core.exception.Failure
import com.ahmedmolawale.dogceo.core.functional.Result
import com.ahmedmolawale.dogceo.features.dogs.domain.model.DogBreed
import com.ahmedmolawale.dogceo.features.dogs.domain.usecases.GetDogBreedsUseCase
import com.ahmedmolawale.dogceo.features.dogs.presentation.mapper.toPresentation
import com.ahmedmolawale.dogceo.features.dogs.presentation.model.state.DogBreedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject

@HiltViewModel
class DogBreedViewModel @Inject constructor(
    private val getDogBreeds: GetDogBreedsUseCase
) : ViewModel() {

    private val job = Job()

    private val _dogBreedState = MutableLiveData<DogBreedState>()
    val dogBreedState: LiveData<DogBreedState>
        get() = _dogBreedState

    fun fetchDogBreeds() {
        _dogBreedState.value = DogBreedState.Loading
        getDogBreeds(job, GetDogBreedsUseCase.None()) {
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
        _dogBreedState.value = DogBreedState.Error("Unable to fetch data")
    }

    private fun handleSuccess(dogBreeds: List<DogBreed>) {
        if (dogBreeds.isEmpty()) {
            _dogBreedState.value = DogBreedState.NoDogBreeds
        } else {
            _dogBreedState.value = DogBreedState.DogBreeds(dogBreeds.map { it.toPresentation() })
        }
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}