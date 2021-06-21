package com.soethihatun.photogallery.features.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.soethihatun.photogallery.data.model.domain.Photo
import com.soethihatun.photogallery.databinding.ItemSearchPhotoBinding

class SearchPhotoAdapter(private val viewModel: SearchPhotoViewModel) :
    ListAdapter<Photo, SearchPhotoAdapter.ViewHolder>(PhotoDiffCallback()) {
    class ViewHolder private constructor(private val binding: ItemSearchPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(viewModel: SearchPhotoViewModel, photo: Photo) {
            binding.viewModel = viewModel
            binding.photo = photo
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemSearchPhotoBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(viewModel, getItem(position))
    }
}

class PhotoDiffCallback : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean =
        oldItem == newItem
}
