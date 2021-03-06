package com.ahmedmolawale.dogceo.features.dogs.presentation.view.items

import android.view.View
import coil.load
import com.ahmedmolawale.dogceo.R
import com.ahmedmolawale.dogceo.databinding.DogBreedImageItemBinding
import com.ahmedmolawale.dogceo.features.dogs.presentation.model.DogBreedImagePresentation
import com.xwray.groupie.viewbinding.BindableItem

class DogBreedImageItem(
    private val breedImage: DogBreedImagePresentation,
) : BindableItem<DogBreedImageItemBinding>() {
    override fun bind(viewBinding: DogBreedImageItemBinding, position: Int) {
        viewBinding.imageSection.clipToOutline = true
        viewBinding.dogImage.load(breedImage.imageUrl) {
            crossfade(true)
            placeholder(R.drawable.dog_image_default_bg)
        }
    }

    override fun getLayout(): Int {
        return R.layout.dog_breed_image_item
    }

    override fun initializeViewBinding(view: View): DogBreedImageItemBinding {
        return DogBreedImageItemBinding.bind(view)
    }

}