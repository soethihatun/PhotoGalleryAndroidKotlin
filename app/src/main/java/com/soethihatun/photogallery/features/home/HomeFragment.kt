package com.soethihatun.photogallery.features.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.soethihatun.photogallery.R
import com.soethihatun.photogallery.core.platform.BaseFragment
import com.soethihatun.photogallery.core.platform.EventObserver
import com.soethihatun.photogallery.data.model.domain.Photo
import com.soethihatun.photogallery.databinding.FragmentHomeBinding
import com.soethihatun.photogallery.util.extension.showShortToast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private lateinit var dataBinding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var photoAdapter: PhotoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@HomeFragment.viewModel
        }

        setHasOptionsMenu(true)

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListAdapter()

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

        viewModel.openSearchPhotoEvent.observe(
            viewLifecycleOwner,
            EventObserver {
                openSearchPhoto()
            }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuSearch -> {
                viewModel.openSearchPhoto()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun setupListAdapter() {
        val viewModel = dataBinding.viewModel
        if (viewModel != null) {
            photoAdapter = PhotoAdapter(viewModel)
            dataBinding.rvPhotos.apply {
                adapter = photoAdapter
            }
        } else {
            Timber.w("ViewModel not initialized when attempting to set up adapter.")
        }
    }

    private fun openPhoto(photo: Photo) {
        HomeFragmentDirections.actionHomeFragmentToFullImageFragment(photoUrl = photo.photoUrl)
            .also {
                findNavController().navigate(it)
            }
    }

    private fun openSearchPhoto() {
        HomeFragmentDirections.actionHomeFragmentToSearchPhotoFragment()
            .also {
                findNavController().navigate(it)
            }
    }
}
