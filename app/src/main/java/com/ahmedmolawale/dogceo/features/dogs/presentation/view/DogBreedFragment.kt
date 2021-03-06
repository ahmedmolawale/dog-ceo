package com.ahmedmolawale.dogceo.features.dogs.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
        dogBreedViewModel.fetchDogBreeds()
        setupView()
        dogBreedViewModel.dogBreedState.observe(viewLifecycleOwner, {
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
        })
    }

    private fun setupView() {
        binding.search.addTextChangedListener {
            if (it != null && it.isNotBlank()) {
                //characterSearchViewModel.searchCharacters(it.toString())
            }
        }
    }

    private fun setupListAdapter(dogBreeds: List<DogBreedPresentation>) {
        context?.let {
            binding.dogBreedRecyclerView.initRecyclerViewWithLineDecoration(it)
        }
        binding.apply {
            dogBreedRecyclerView.visibility = VISIBLE
            shimmerViewContainer.visibility = GONE
            errorOnDogBreeds.visibility = GONE
            noDogBreed.visibility = GONE
        }
        adapter.addAll(
            dogBreeds.map {
                val dogBreedGroup =
                    ExpandableGroup(DogBreedItem(it) { dogBreed -> onDogBreedClick(dogBreed) })
                dogBreedGroup.addAll(it.subBreeds.map { s ->
                    DogSubBreedItem(s) { dogBreed -> onDogSubBreedClick(dogBreed) }
                })
                dogBreedGroup
            }
        )
        binding.dogBreedRecyclerView.adapter = adapter
    }

    private fun onDogBreedClick(dogBreedPresentation: DogBreedPresentation) {
        Log.e("Dog", dogBreedPresentation.toString())
    }

    private fun onDogSubBreedClick(dogSubBreedPresentation: DogSubBreedPresentation) {
        Log.e("Dog Breed", dogSubBreedPresentation.toString())
    }

    private fun showError(errorMessage: String) {
        binding.apply {
            shimmerViewContainer.visibility = GONE
            errorOnDogBreeds.visibility = VISIBLE
            errorOnDogBreeds.text = errorMessage
            noDogBreed.visibility = GONE
        }
    }

    private fun showLoading() {
        binding.apply {
            shimmerViewContainer.visibility = VISIBLE
            errorOnDogBreeds.visibility = GONE
            noDogBreed.visibility = GONE
        }
    }

    private fun showNoDogBreeds() {
        binding.apply {
            shimmerViewContainer.visibility = GONE
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
    }
}
