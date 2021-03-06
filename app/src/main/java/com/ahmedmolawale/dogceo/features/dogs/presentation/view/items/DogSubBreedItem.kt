package com.ahmedmolawale.dogceo.features.dogs.presentation.view.items

import android.view.View
import com.ahmedmolawale.dogceo.R
import com.ahmedmolawale.dogceo.databinding.DogSubBreedItemBinding
import com.ahmedmolawale.dogceo.features.dogs.presentation.model.DogSubBreedPresentation
import com.xwray.groupie.viewbinding.BindableItem

class DogSubBreedItem(
    private val subBreed: DogSubBreedPresentation,
    private val subBreedClick: (DogSubBreedPresentation) -> Unit
) : BindableItem<DogSubBreedItemBinding>() {
    override fun bind(viewBinding: DogSubBreedItemBinding, position: Int) {
        viewBinding.dogSubbreed = subBreed
        viewBinding.root.setOnClickListener {
            subBreedClick(subBreed)
        }
        viewBinding.executePendingBindings()
    }

    override fun getLayout(): Int {
        return R.layout.dog_sub_breed_item
    }

    override fun initializeViewBinding(view: View): DogSubBreedItemBinding {
        return DogSubBreedItemBinding.bind(view)
    }
}
