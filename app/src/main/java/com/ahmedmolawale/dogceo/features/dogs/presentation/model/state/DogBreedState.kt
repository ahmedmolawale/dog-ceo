package com.ahmedmolawale.dogceo.features.dogs.presentation.model.state

import com.ahmedmolawale.dogceo.features.dogs.presentation.model.DogBreedPresentation

sealed class DogBreedState {
    object Loading : DogBreedState()
    data class Error(val errorMessage: String) : DogBreedState()
    data class DogBreeds(val dogBreeds: List<DogBreedPresentation>) : DogBreedState()
    object NoDogBreeds : DogBreedState()
}