package com.ahmedmolawale.dogceo.features.dogs.domain.usecases

import com.ahmedmolawale.dogceo.core.baseinteractor.BaseUseCase
import com.ahmedmolawale.dogceo.core.functional.Result
import com.ahmedmolawale.dogceo.features.dogs.domain.model.DogBreed
import com.ahmedmolawale.dogceo.features.dogs.domain.repository.IDogBreedsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDogBreedsUseCase @Inject constructor(private val dogBreedsRepository: IDogBreedsRepository) :
    BaseUseCase<GetDogBreedsUseCase.None, List<DogBreed>>() {

    override suspend fun run(params: None): Flow<Result<List<DogBreed>>> {
        return dogBreedsRepository.getDogBreeds()
    }

    class None
}
