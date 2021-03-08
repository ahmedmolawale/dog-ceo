package com.ahmedmolawale.dogceo.features.dogs.presentation.model.state

import androidx.annotation.StringRes
import com.ahmedmolawale.dogceo.features.dogs.presentation.model.DogBreedPresentation

sealed class DogBreedState {
    object Loading : DogBreedState()
    data class Error(@StringRes val errorMessageId: Int) : DogBreedState()
    data class DogBreeds(val dogBreeds: List<DogBreedPresentation>) : DogBreedState()
    object NoDogBreeds : DogBreedState()
}
