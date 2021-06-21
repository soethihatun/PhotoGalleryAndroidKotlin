package com.soethihatun.photogallery.features.home

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soethihatun.photogallery.data.model.domain.Photo

@BindingAdapter("app:photoItems")
fun setPhotoItems(listView: RecyclerView, items: List<Photo>) {
    (listView.adapter as PhotoAdapter).submitList(items)
}

@BindingAdapter("app:photoSpanCount")
fun setPhotoSpans(listView: RecyclerView, spanCount: Int) {
    (listView.layoutManager as? GridLayoutManager)?.spanCount = spanCount
}
