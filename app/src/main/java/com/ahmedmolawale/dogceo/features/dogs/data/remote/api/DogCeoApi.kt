package com.ahmedmolawale.dogceo.features.dogs.data.remote.api

import com.ahmedmolawale.dogceo.features.dogs.data.remote.model.DogBreedImageResponse
import com.ahmedmolawale.dogceo.features.dogs.data.remote.model.DogBreedResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DogCeoApi {

    @GET("breeds/list/all/")
    suspend fun fetchDogBreeds(): Response<DogBreedResponse>

    @GET("breed/{breed_name}/images/")
    suspend fun fetchDogBreedImages(@Path("breed_name") breedName: String): Response<DogBreedImageResponse>

    @GET("breed/{breed_name}/{sub_breed_name}/images/")
    suspend fun fetchDogSubBreedImages(
        @Path("breed_name") breedName: String,
        @Path("sub_breed_name") subBreedName: String
    ): Response<DogBreedImageResponse>
}
