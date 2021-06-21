package com.soethihatun.photogallery.features.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.soethihatun.photogallery.core.platform.BaseFragment
import com.soethihatun.photogallery.core.platform.EventObserver
import com.soethihatun.photogallery.data.model.domain.Photo
import com.soethihatun.photogallery.databinding.FragmentSearchPhotoBinding
import com.soethihatun.photogallery.util.DebouncingQueryTextListener
import com.soethihatun.photogallery.util.extension.showShortToast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SearchPhotoFragment : BaseFragment() {

    private lateinit var dataBinding: FragmentSearchPhotoBinding

    private val viewModel: SearchPhotoViewModel by viewModels()

    private lateinit var searchPhotoAdapter: SearchPhotoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = FragmentSearchPhotoBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@SearchPhotoFragment.viewModel
        }
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListAdapter()

        dataBinding.searchView.setOnQueryTextListener(
            DebouncingQueryTextListener(this@SearchPhotoFragment.lifecycle) { newText ->
                if (newText.isNullOrEmpty()) {
                    viewModel.resetSearchPhotos()
                } else {
                    viewModel.searchPhotos(newText)
                }
            }
        )

        viewModel.message.observe(
            viewLifecycleOwner,
            EventObserver {
                showShortToast(it)
            }
        )

        viewModel.openPhotoEvent.observe(
            viewLifecycleOwner,
            EventObserver { photo ->
                openPhoto(photo)
            }
        )
    }

    private fun setupListAdapter() {
        val viewModel = dataBinding.viewModel
        if (viewModel != null) {
            searchPhotoAdapter = SearchPhotoAdapter(viewModel)
            dataBinding.rvPhotos.apply {
                adapter = searchPhotoAdapter
            }
        } else {
            Timber.w("ViewModel not initialized when attempting to set up adapter.")
        }
    }

    private fun openPhoto(photo: Photo) {
        SearchPhotoFragmentDirections.actionSearchPhotoFragmentToFullImageFragment(photoUrl = photo.photoUrl)
            .also {
                findNavController().navigate(it)
            }
    }
}
