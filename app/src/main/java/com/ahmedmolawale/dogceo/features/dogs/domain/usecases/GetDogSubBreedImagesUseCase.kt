package com.ahmedmolawale.dogceo.features.dogs.domain.usecases

import com.ahmedmolawale.dogceo.core.baseinteractor.BaseUseCase
import com.ahmedmolawale.dogceo.core.functional.Result
import com.ahmedmolawale.dogceo.features.dogs.domain.model.DogBreedImage
import com.ahmedmolawale.dogceo.features.dogs.domain.repository.IDogBreedImagesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDogSubBreedImagesUseCase @Inject constructor(private val dogBreedImagesRepository: IDogBreedImagesRepository) :
    BaseUseCase<GetDogSubBreedImagesUseCase.DogSubBreedParams, List<DogBreedImage>>() {
    override suspend fun run(params: DogSubBreedParams): Flow<Result<List<DogBreedImage>>> {
        return dogBreedImagesRepository.getDogSubBreedImages(params.breedName, params.subBreedName)
    }

    data class DogSubBreedParams(val breedName: String, val subBreedName: String)
}
