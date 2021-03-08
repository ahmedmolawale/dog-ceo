package com.ahmedmolawale.dogceo.sampledata

val dogBreeds = """
    {
      "status": "success",
      "message": {
         "affenpinscher": [],
          "african": [],
          "airedale": [],
          "akita": [],
          "appenzeller": [],
          "australian": [
              "shepherd"
          ]
      }
    }
""".trimIndent()

val emptyDogBreeds = """
    {
      "status": "success",
      "message": {
      }
    }
""".trimIndent()

val dogBreedImages = """
    {
      "status": "success",
      "message": [
          "https://images.dog.ceo/breeds/hound-walker/n02089867_1918.jpg",
          "https://images.dog.ceo/breeds/hound-walker/n02089867_1921.jpg",
          "https://images.dog.ceo/breeds/hound-walker/n02089867_1931.jpg",
          "https://images.dog.ceo/breeds/hound-walker/n02089867_1965.jpg",
          "https://images.dog.ceo/breeds/hound-walker/n02089867_1987.jpg",
          "https://images.dog.ceo/breeds/hound-walker/n02089867_1988.jpg"
      ]
    }
""".trimIndent()

val emptyDogBreedImages = """
    {
      "status": "success",
      "message": [
      ]
    }
""".trimIndent()
