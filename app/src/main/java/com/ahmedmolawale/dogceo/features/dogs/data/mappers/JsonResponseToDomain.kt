package com.ahmedmolawale.dogceo.features.dogs.data.mappers

import com.ahmedmolawale.dogceo.features.dogs.data.remote.model.DogBreedImageResponse
import com.ahmedmolawale.dogceo.features.dogs.data.remote.model.DogBreedResponse
import com.ahmedmolawale.dogceo.features.dogs.domain.model.DogBreed
import com.ahmedmolawale.dogceo.features.dogs.domain.model.DogBreedImage
import com.ahmedmolawale.dogceo.features.dogs.domain.model.DogSubBreed

fun DogBreedResponse.toDomain(): List<DogBreed> {
    return this.message.entries.map {
        val subBreed = it.value.map { sub ->
            DogSubBreed(sub)
        }
        DogBreed(it.key, subBreed)
    }
}

fun DogBreedImageResponse.toDomain(): List<DogBreedImage> {
    return this.message.map {
        DogBreedImage(imageUrl = it)
    }
}
