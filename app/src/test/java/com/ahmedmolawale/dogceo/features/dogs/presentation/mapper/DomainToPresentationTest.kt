package com.ahmedmolawale.dogceo.features.dogs.presentation.mapper

import com.ahmedmolawale.dogceo.UnitTest
import com.ahmedmolawale.dogceo.features.dogs.domain.model.DogBreed
import com.ahmedmolawale.dogceo.features.dogs.domain.model.DogSubBreed
import com.ahmedmolawale.dogceo.features.dogs.presentation.model.DogBreedPresentation
import com.ahmedmolawale.dogceo.features.dogs.presentation.model.DogSubBreedPresentation
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class DomainToPresentationTest : UnitTest() {
    @Test
    fun `dogBreedDomain to presentation should return presentation with empty subbreed`() {
        val dogBreed = DogBreed(
            name = "hound",
            subBreed = emptyList()
        )
        val expected = DogBreedPresentation(breedNameInitial = "H", breedName = "Hound", subBreeds = emptyList())
        assertThat(dogBreed.toPresentation()).isEqualTo(expected)
    }

    @Test
    fun `dogBreedDomain to presentation should return presentation with subbreeds`() {
        val dogBreed = DogBreed(
            name = "hound",
            subBreed = listOf(DogSubBreed("afs"))
        )
        val expected = DogBreedPresentation(
            breedNameInitial = "H", breedName = "Hound",
            subBreeds = listOf(
                DogSubBreedPresentation(breedName = "Afs", breedNameInitial = "A", parentBreedName = "Hound")
            )
        )
        assertThat(dogBreed.toPresentation()).isEqualTo(expected)
    }
}
