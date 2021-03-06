package com.ahmedmolawale.dogceo.features.dogs.presentation.view.items

import android.view.View
import com.ahmedmolawale.dogceo.R
import com.ahmedmolawale.dogceo.databinding.DogBreedItemBinding
import com.ahmedmolawale.dogceo.features.dogs.presentation.model.DogBreedPresentation
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.viewbinding.BindableItem

class DogBreedItem(
    private val dogBreed: DogBreedPresentation,
    private val dogBreedClick: (DogBreedPresentation) -> Unit
) :
    BindableItem<DogBreedItemBinding>(), ExpandableItem {

    private lateinit var expandableGroup: ExpandableGroup
    override fun bind(viewBinding: DogBreedItemBinding, position: Int) {
        viewBinding.dogbreed = dogBreed
        viewBinding.root.setOnClickListener {
            dogBreedClick(dogBreed)
        }
        viewBinding.showBreed.apply {
            setOnClickListener {
                expandableGroup.onToggleExpanded()
                text = if (expandableGroup.isExpanded) context.getText(R.string.hide_sub_breed)
                else context.getText(R.string.show_sub_breed)
            }
        }
        viewBinding.executePendingBindings()
    }

    override fun getLayout(): Int {
        return R.layout.dog_breed_item
    }

    override fun initializeViewBinding(view: View): DogBreedItemBinding {
        return DogBreedItemBinding.bind(view)
    }

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        expandableGroup = onToggleListener
    }
}