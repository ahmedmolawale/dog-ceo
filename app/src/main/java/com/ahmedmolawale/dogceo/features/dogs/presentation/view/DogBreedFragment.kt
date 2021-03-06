package com.ahmedmolawale.dogceo.features.dogs.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ahmedmolawale.dogceo.R
import com.ahmedmolawale.dogceo.databinding.DogbreedFragmentBinding
import com.ahmedmolawale.dogceo.features.dogs.presentation.initRecyclerViewWithLineDecoration
import com.ahmedmolawale.dogceo.features.dogs.presentation.model.DogBreedPresentation
import com.ahmedmolawale.dogceo.features.dogs.presentation.model.DogSubBreedPresentation
import com.ahmedmolawale.dogceo.features.dogs.presentation.model.state.DogBreedState
import com.ahmedmolawale.dogceo.features.dogs.presentation.view.items.DogBreedItem
import com.ahmedmolawale.dogceo.features.dogs.presentation.view.items.DogSubBreedItem
import com.ahmedmolawale.dogceo.features.dogs.presentation.viewmodel.DogBreedViewModel
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DogBreedFragment : Fragment() {

    private var _binding: DogbreedFragmentBinding? = null
    private val binding get() = _binding!!
    private val dogBreedViewModel: DogBreedViewModel by viewModels()
    private val adapter = GroupieAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DogbreedFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        dogBreedViewModel.fetchDogBreeds()
        dogBreedViewModel.dogBreedState.observe(
            viewLifecycleOwner,
            {
                when (it) {
                    is DogBreedState.DogBreeds -> {
                        setupListAdapter(it.dogBreeds)
                    }
                    is DogBreedState.NoDogBreeds -> {
                        showNoDogBreeds()
                    }
                    is DogBreedState.Error -> {
                        showError(it.errorMessage)
                    }
                    is DogBreedState.Loading -> {
                        showLoading()
                    }
                }
            }
        )
    }

    private fun setupView() {
        context?.let {
            binding.dogBreedRecyclerView.initRecyclerViewWithLineDecoration(it)
        }
        binding.dogBreedRecyclerView.adapter = adapter
        binding.header.headerTitle.text = context?.getText(R.string.dog_breeds_title)
        binding.search.addTextChangedListener {
            if (it != null) {
                dogBreedViewModel.searchDogBreeds(it.toString())
            }
        }
    }

    private fun setupListAdapter(dogBreeds: List<DogBreedPresentation>) {
        binding.apply {
            dogBreedRecyclerView.visibility = VISIBLE
            shimmerViewContainer.visibility = GONE
            errorOnDogBreeds.visibility = GONE
            noDogBreed.visibility = GONE
        }
        adapter.clear()
        adapter.addAll(
            dogBreeds.map {
                val dogBreedGroup =
                    ExpandableGroup(DogBreedItem(it) { dogBreed -> onDogBreedClick(dogBreed) })
                dogBreedGroup.addAll(
                    it.subBreeds.map { s ->
                        DogSubBreedItem(s) { dogBreed -> onDogSubBreedClick(dogBreed) }
                    }
                )
                dogBreedGroup
            }
        )
    }

    private fun onDogBreedClick(dogBreedPresentation: DogBreedPresentation) {
        openDogImages(dogBreedPresentation.breedName)
    }

    private fun onDogSubBreedClick(dogSubBreedPresentation: DogSubBreedPresentation) {
        openDogImages(dogSubBreedPresentation.parentBreedName, dogSubBreedPresentation.breedName)
    }

    private fun openDogImages(breedName: String, subBreedName: String? = null) {
        val fm = activity?.supportFragmentManager
        val dogBreedImageFragment =
            DogBreedImageFragment()
        dogBreedImageFragment.arguments = bundleOf(
            BREED_NAME to breedName.toLowerCase(Locale.getDefault()),
            SUB_BREED_NAME to subBreedName?.toLowerCase(Locale.getDefault())
        )
        fm?.beginTransaction()
            ?.add(R.id.main_host, dogBreedImageFragment, DogBreedImageFragment.TAG)
            ?.addToBackStack(DogBreedImageFragment.TAG)?.commit()
    }

    private fun showError(errorMessage: String) {
        binding.apply {
            shimmerViewContainer.visibility = GONE
            errorOnDogBreeds.visibility = VISIBLE
            errorOnDogBreeds.text = errorMessage
            noDogBreed.visibility = GONE
            dogBreedRecyclerView.visibility = GONE
        }
    }

    private fun showLoading() {
        binding.apply {
            shimmerViewContainer.visibility = VISIBLE
            errorOnDogBreeds.visibility = GONE
            noDogBreed.visibility = GONE
            dogBreedRecyclerView.visibility = GONE
        }
    }

    private fun showNoDogBreeds() {
        binding.apply {
            shimmerViewContainer.visibility = GONE
            dogBreedRecyclerView.visibility = GONE
            errorOnDogBreeds.visibility = GONE
            noDogBreed.visibility = VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val TAG = "DogBreedFragment"
        val BREED_NAME = "breed_name"
        val SUB_BREED_NAME = "sub_breed_name"
    }
}
