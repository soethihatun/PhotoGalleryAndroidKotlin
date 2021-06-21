package com.soethihatun.photogallery.util.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide

object ImageBindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["app:srcUrl", "app:placeholderUrl"], requireAll = false)
    fun setImageUrl(imageView: ImageView, url: String? = null, placeholder: Drawable? = null) {
        val default = placeholder ?: CircularProgressDrawable(imageView.context).apply {
            strokeWidth = 5f
            centerRadius = 30f
            start()
        }
        Glide.with(imageView.context)
            .load(url)
            .placeholder(default)
            .into(imageView)
    }
}
