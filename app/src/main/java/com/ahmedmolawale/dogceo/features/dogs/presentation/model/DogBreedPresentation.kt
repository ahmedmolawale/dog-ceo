package com.ahmedmolawale.dogceo.features.dogs.presentation.model

data class DogBreedPresentation(
    val breedNameInitial: String,
    val breedName: String,
    val subBreeds: List<DogSubBreedPresentation>
)

data class DogSubBreedPresentation(
    val breedNameInitial: String,
    val parentBreedName: String,
    val breedName: String
)
