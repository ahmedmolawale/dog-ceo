package com.ahmedmolawale.dogceo.features.dogs.domain.repository

import com.ahmedmolawale.dogceo.core.functional.Result
import com.ahmedmolawale.dogceo.features.dogs.domain.model.DogBreed
import kotlinx.coroutines.flow.Flow

interface IDogBreedsRepository {
    suspend fun getDogBreeds(): Flow<Result<List<DogBreed>>>
}
