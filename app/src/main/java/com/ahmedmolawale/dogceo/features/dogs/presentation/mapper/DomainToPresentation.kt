package com.ahmedmolawale.dogceo.features.dogs.presentation.mapper

import com.ahmedmolawale.dogceo.features.dogs.domain.model.DogBreed
import com.ahmedmolawale.dogceo.features.dogs.domain.model.DogBreedImage
import com.ahmedmolawale.dogceo.features.dogs.presentation.model.DogBreedImagePresentation
import com.ahmedmolawale.dogceo.features.dogs.presentation.model.DogBreedPresentation
import com.ahmedmolawale.dogceo.features.dogs.presentation.model.DogSubBreedPresentation
import java.util.*

fun DogBreed.toPresentation(): DogBreedPresentation {
    val subBreedsPresentation = this.subBreed.map {
        DogSubBreedPresentation(
            breedNameInitial = it.name[0]
                .toUpperCase().toString(),
            parentBreedName = this.name.capitalize(Locale.getDefault()),
            breedName = it.name.capitalize(Locale.getDefault())
        )
    }
    return DogBreedPresentation(
        breedNameInitial = this.name[0].toUpperCase().toString(),
        breedName = this.name.capitalize(Locale.getDefault()),
        subBreeds = subBreedsPresentation
    )
}

fun DogBreedImage.toPresentation() = DogBreedImagePresentation(this.imageUrl)