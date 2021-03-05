package com.ahmedmolawale.dogceo.features.dogs.data.remote.model

data class DogBreedResponse(
    val status: String,
    val message: Map<String, List<String>>
) {
    companion object {
        const val SUCCESS_STATUS = "success"
    }
}
