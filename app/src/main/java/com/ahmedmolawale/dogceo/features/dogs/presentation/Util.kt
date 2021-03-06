package com.ahmedmolawale.dogceo.features.dogs.presentation

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmedmolawale.dogceo.R

@SuppressLint("UseCompatLoadingForDrawables")
fun RecyclerView.initRecyclerViewWithLineDecoration(context: Context) {
    val linearLayoutManager = LinearLayoutManager(context)
    val itemDecoration = DividerItemDecoration(context, linearLayoutManager.orientation).apply {
        setDrawable(context.getDrawable(R.drawable.line)!!)
    }
    layoutManager = linearLayoutManager
    addItemDecoration(itemDecoration)
}