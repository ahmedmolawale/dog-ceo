package com.ahmedmolawale.dogceo.features.dogs.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ahmedmolawale.dogceo.databinding.DogBreedImagesFragmentBinding
import com.ahmedmolawale.dogceo.features.dogs.presentation.initRecyclerViewUsingGridLayout
import com.ahmedmolawale.dogceo.features.dogs.presentation.model.DogBreedImagePresentation
import com.ahmedmolawale.dogceo.features.dogs.presentation.model.state.DogBreedImagesState
import com.ahmedmolawale.dogceo.features.dogs.presentation.view.items.DogBreedImageItem
import com.ahmedmolawale.dogceo.features.dogs.presentation.viewmodel.DogBreedImagesViewModel
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DogBreedImageFragment : Fragment() {

    private var _binding: DogBreedImagesFragmentBinding? = null
    private val binding get() = _binding!!
    private val dogBreedImagesViewModel: DogBreedImagesViewModel by viewModels()
    private val adapter = GroupieAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DogBreedImagesFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val breedName =
            requireArguments().getString(DogBreedFragment.BREED_NAME)
        val subBreedName =
            requireArguments().getString(DogBreedFragment.SUB_BREED_NAME)

        breedName?.let {
            if (subBreedName != null) {
                binding.breedName.text = breedName.plus("-").plus(subBreedName)
                    .capitalize(Locale.getDefault())
                dogBreedImagesViewModel.fetchDogSubBreedImages(it, subBreedName)
            } else {
                binding.breedName.text = breedName.capitalize(Locale.getDefault())
                dogBreedImagesViewModel.fetchDogBreedImages(it)
            }
        }

        dogBreedImagesViewModel.dogBreedImagesState.observe(viewLifecycleOwner, {
            when (it) {
                is DogBreedImagesState.DogBreedImages -> {
                    setupListAdapter(it.dogBreedImages)
                }
                is DogBreedImagesState.NoDogBreedImages -> {
                    showNoDogBreedImages()
                }
                is DogBreedImagesState.Error -> {
                    showError(it.errorMessage)
                }
                is DogBreedImagesState.Loading -> {
                    showLoading()
                }
            }
        })
    }

    private fun setupListAdapter(dogBreedImages: List<DogBreedImagePresentation>) {
        context?.let {
            binding.dogImages.initRecyclerViewUsingGridLayout(it, 2)
        }
        adapter.addAll(
            dogBreedImages.map { DogBreedImageItem(it) }
        )
        binding.apply {
            dogImages.visibility = VISIBLE
            dogImages.adapter = adapter
            shimmerViewContainer.visibility = GONE
            errorOnDogBreedImages.visibility = GONE
            noDogBreedImages.visibility = GONE
        }
    }

    private fun showError(errorMessage: String) {
        binding.apply {
            shimmerViewContainer.visibility = GONE
            errorOnDogBreedImages.visibility = VISIBLE
            errorOnDogBreedImages.text = errorMessage
            dogImages.visibility = GONE
            noDogBreedImages.visibility = GONE
        }
    }

    private fun showLoading() {
        binding.apply {
            shimmerViewContainer.visibility = VISIBLE
            errorOnDogBreedImages.visibility = GONE
            dogImages.visibility = GONE
            noDogBreedImages.visibility = GONE
        }
    }

    private fun showNoDogBreedImages() {
        binding.apply {
            shimmerViewContainer.visibility = GONE
            errorOnDogBreedImages.visibility = GONE
            dogImages.visibility = GONE
            noDogBreedImages.visibility = VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val TAG = "DogBreedImagesFragment"
    }
}
