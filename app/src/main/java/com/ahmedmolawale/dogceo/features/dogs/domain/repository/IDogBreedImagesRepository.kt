package com.ahmedmolawale.dogceo.features.dogs.domain.repository

import com.ahmedmolawale.dogceo.core.functional.Result
import com.ahmedmolawale.dogceo.features.dogs.domain.model.DogBreedImage
import kotlinx.coroutines.flow.Flow

interface IDogBreedImagesRepository {
    suspend fun getDogBreedImages(breedName: String): Flow<Result<List<DogBreedImage>>>
    suspend fun getDogSubBreedImages(
        breedName: String,
        subBreedName: String
    ): Flow<Result<List<DogBreedImage>>>
}
