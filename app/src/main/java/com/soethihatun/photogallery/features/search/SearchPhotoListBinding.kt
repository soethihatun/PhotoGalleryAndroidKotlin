package com.soethihatun.photogallery.features.search

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.soethihatun.photogallery.data.model.domain.Photo

@BindingAdapter("app:searchPhotoItems")
fun setPhotoItems(listView: RecyclerView, items: List<Photo>) {
    (listView.adapter as SearchPhotoAdapter).submitList(items)
}
