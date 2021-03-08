package com.ahmedmolawale.dogceo.features.dogs.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmedmolawale.dogceo.R
import com.ahmedmolawale.dogceo.core.exception.Failure
import com.ahmedmolawale.dogceo.core.functional.Result
import com.ahmedmolawale.dogceo.core.idlingresource.EspressoIdlingResource
import com.ahmedmolawale.dogceo.features.dogs.domain.model.DogBreed
import com.ahmedmolawale.dogceo.features.dogs.domain.usecases.GetDogBreedsUseCase
import com.ahmedmolawale.dogceo.features.dogs.presentation.mapper.toPresentation
import com.ahmedmolawale.dogceo.features.dogs.presentation.model.DogBreedPresentation
import com.ahmedmolawale.dogceo.features.dogs.presentation.model.state.DogBreedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DogBreedViewModel @Inject constructor(
    private val getDogBreeds: GetDogBreedsUseCase
) : ViewModel() {

    private val job = Job()

    var dogBreedsPresentation = listOf<DogBreedPresentation>()
    private val _dogBreedState = MutableLiveData<DogBreedState>()
    val dogBreedState: LiveData<DogBreedState>
        get() = _dogBreedState

    fun fetchDogBreeds() {
        EspressoIdlingResource.increment()
        _dogBreedState.value = DogBreedState.Loading
        getDogBreeds(job, GetDogBreedsUseCase.None()) {
            EspressoIdlingResource.decrement()
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
        _dogBreedState.value = DogBreedState.Error(R.string.errorMessage)
    }

    private fun handleSuccess(dogBreeds: List<DogBreed>) {
        if (dogBreeds.isEmpty()) {
            _dogBreedState.value = DogBreedState.NoDogBreeds
        } else {
            dogBreedsPresentation = dogBreeds.map { it.toPresentation() }
            _dogBreedState.value = DogBreedState.DogBreeds(dogBreedsPresentation)
        }
    }

    fun searchDogBreeds(query: String) {
        _dogBreedState.value = DogBreedState.Loading
        if (query.isBlank()) {
            _dogBreedState.value = DogBreedState.DogBreeds(dogBreedsPresentation)
        } else {
            val dogBreedsFilter = dogBreedsPresentation.filter {
                it.breedName.toLowerCase(Locale.getDefault()).contains(
                    query.trim().toLowerCase(
                        Locale.getDefault()
                    )
                )
            }
            if (dogBreedsFilter.isEmpty())
                _dogBreedState.value = DogBreedState.NoDogBreeds
            else
                _dogBreedState.value = DogBreedState.DogBreeds(dogBreedsFilter)
        }
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}
