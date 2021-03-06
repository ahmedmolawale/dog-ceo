package com.ahmedmolawale.dogceo.features.dogs.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ahmedmolawale.dogceo.R
import com.ahmedmolawale.dogceo.features.dogs.presentation.view.DogBreedFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dogBreedFragment =
            DogBreedFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.main_host, dogBreedFragment, DogBreedFragment.TAG)
            .commit()
    }
}
