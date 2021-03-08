package com.ahmedmolawale.dogceo.features.dogs.presentation.model.state

import androidx.annotation.StringRes
import com.ahmedmolawale.dogceo.features.dogs.presentation.model.DogBreedImagePresentation

sealed class DogBreedImagesState {
    object Loading : DogBreedImagesState()
    data class Error(@StringRes val errorMessageId: Int) : DogBreedImagesState()
    data class DogBreedImages(val dogBreedImages: List<DogBreedImagePresentation>) :
        DogBreedImagesState()

    object NoDogBreedImages : DogBreedImagesState()
}
