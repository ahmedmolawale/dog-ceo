package com.ahmedmolawale.dogceo.features.dogs.domain.model

data class DogBreed(
    private val name: String,
    private val subBreed: List<DogSubBreed>
)

data class DogSubBreed(
    private val name: String
)
