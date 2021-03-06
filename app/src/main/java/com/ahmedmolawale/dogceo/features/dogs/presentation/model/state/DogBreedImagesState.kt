package com.ahmedmolawale.dogceo.features.dogs.presentation.model.state

import com.ahmedmolawale.dogceo.features.dogs.presentation.model.DogBreedImagePresentation

sealed class DogBreedImagesState {
    object Loading : DogBreedImagesState()
    data class Error(val errorMessage: String) : DogBreedImagesState()
    data class DogBreedImages(val dogBreedImages: List<DogBreedImagePresentation>) :
        DogBreedImagesState()

    object NoDogBreedImages : DogBreedImagesState()
}
