package com.soethihatun.photogallery.features.fullimage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.soethihatun.photogallery.core.platform.BaseFragment
import com.soethihatun.photogallery.databinding.FragmentFullImageBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.max
import kotlin.math.min

@AndroidEntryPoint
class FullImageFragment : BaseFragment() {

    private lateinit var dataBinding: FragmentFullImageBinding

    private val viewModel: FullImageViewModel by viewModels()

    private val args: FullImageFragmentArgs by navArgs()

    private val scaleGestureDetector: ScaleGestureDetector by lazy {
        ScaleGestureDetector(requireContext(), ZoomInOutScaleListener(dataBinding.ivPhoto))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = FragmentFullImageBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@FullImageFragment.viewModel
        }
        dataBinding.root.setOnTouchListener { v, event ->
            scaleGestureDetector.onTouchEvent(event)
            v.performClick()
            true
        }
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init(args.photoUrl)
    }

    /**
     * The class handles zoom in and out of the view.
     */
    private inner class ZoomInOutScaleListener(
        val view: View,
        private var scaleFactor: Float = 1.0f
    ) : ScaleGestureDetector.SimpleOnScaleGestureListener() {

        override fun onScale(detector: ScaleGestureDetector?): Boolean {
            scaleFactor *= scaleGestureDetector.scaleFactor
            scaleFactor = max(0.1f, min(scaleFactor, 10.0f))
            view.scaleX = scaleFactor
            view.scaleY = scaleFactor
            return true
        }
    }
}
