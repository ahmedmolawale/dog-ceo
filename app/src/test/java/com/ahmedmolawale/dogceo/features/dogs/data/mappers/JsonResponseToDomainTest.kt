package com.ahmedmolawale.dogceo.features.dogs.data.mappers

import com.ahmedmolawale.dogceo.UnitTest
import com.ahmedmolawale.dogceo.features.dogs.data.remote.model.DogBreedImageResponse
import com.ahmedmolawale.dogceo.features.dogs.data.remote.model.DogBreedResponse
import com.ahmedmolawale.dogceo.features.dogs.domain.model.DogBreed
import com.ahmedmolawale.dogceo.features.dogs.domain.model.DogBreedImage
import com.ahmedmolawale.dogceo.features.dogs.domain.model.DogSubBreed
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class JsonResponseToDomainTest : UnitTest() {

    @Test
    fun `dogBreedResponse to domain should return empty list`() {
        val dogBreedResponse = DogBreedResponse(
            status = "success",
            message =
            mapOf()
        )
        assertThat(dogBreedResponse.toDomain()).isEmpty()
    }

    @Test
    fun `dogBreedResponse to domain should return list`() {
        val dogBreedResponse = DogBreedResponse(
            status = "success",
            message =
            mapOf(
                "hound" to listOf("afghan", "basset"),
                "australian" to listOf("shepherd")
            )
        )
        val expected = listOf(
            DogBreed(
                "hound",
                subBreed = listOf(DogSubBreed("afghan"), DogSubBreed("basset"))
            ),
            DogBreed(
                "australian",
                subBreed = listOf(DogSubBreed("shepherd"))
            )
        )
        assertThat(dogBreedResponse.toDomain()).isNotEmpty()
        assertThat(dogBreedResponse.toDomain()).isEqualTo(expected)
    }

    @Test
    fun `dogBreedImagesResponse to domain should return empty list`() {
        val dogBreedImageResponse = DogBreedImageResponse(
            status = "success",
            message =
            listOf()
        )
        assertThat(dogBreedImageResponse.toDomain()).isEmpty()
    }

    @Test
    fun `dogBreedImagesResponse to domain should return list`() {
        val dogBreedImageResponse = DogBreedImageResponse(
            status = "success",
            message =
            listOf(
                "https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg",
                "https://images.dog.ceo/breeds/hound-afghan/n02088094_1007.jpg"
            )
        )
        val expected = listOf(
            DogBreedImage(
                "https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg"
            ),
            DogBreedImage(
                "https://images.dog.ceo/breeds/hound-afghan/n02088094_1007.jpg"
            ),
        )
        assertThat(dogBreedImageResponse.toDomain()).isNotEmpty()
        assertThat(dogBreedImageResponse.toDomain()).isEqualTo(expected)
    }
}
