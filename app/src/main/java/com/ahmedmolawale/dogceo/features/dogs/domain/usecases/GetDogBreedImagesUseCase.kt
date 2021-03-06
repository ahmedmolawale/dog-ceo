package com.ahmedmolawale.dogceo.features.dogs.domain.usecases

import com.ahmedmolawale.dogceo.core.baseinteractor.BaseUseCase
import com.ahmedmolawale.dogceo.core.functional.Result
import com.ahmedmolawale.dogceo.features.dogs.domain.model.DogBreedImage
import com.ahmedmolawale.dogceo.features.dogs.domain.repository.IDogBreedImagesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDogBreedImagesUseCase @Inject constructor(private val dogBreedImagesRepository: IDogBreedImagesRepository) :
    BaseUseCase<String, List<DogBreedImage>>() {
    override suspend fun run(params: String): Flow<Result<List<DogBreedImage>>> {
        return dogBreedImagesRepository.getDogBreedImages(params)
    }
}
