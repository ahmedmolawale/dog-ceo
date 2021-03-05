package com.ahmedmolawale.dogceo.features.dogs.data.repository

import com.ahmedmolawale.dogceo.core.exception.Failure
import com.ahmedmolawale.dogceo.core.functional.Result
import com.ahmedmolawale.dogceo.features.dogs.data.mappers.toDomain
import com.ahmedmolawale.dogceo.features.dogs.data.remote.api.DogCeoApi
import com.ahmedmolawale.dogceo.features.dogs.data.remote.model.DogBreedResponse
import com.ahmedmolawale.dogceo.features.dogs.domain.model.DogBreed
import com.ahmedmolawale.dogceo.features.dogs.domain.repository.IDogBreedsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DogBreedsRepository @Inject constructor(
    private val apiService: DogCeoApi
) : IDogBreedsRepository {

    override suspend fun getDogBreeds(): Flow<Result<List<DogBreed>>> =
        flow {
            val res = apiService.fetchDogBreeds()
            emit(
                when (res.isSuccessful) {
                    true -> {
                        res.body()?.let { it ->
                            if (it.status == DogBreedResponse.SUCCESS_STATUS) {
                                Result.Success(it.toDomain())
                            } else Result.Error(Failure.DataError)
                        } ?: Result.Error(Failure.DataError)
                    }
                    false -> Result.Error(Failure.ServerError)
                }
            )
        }
}
