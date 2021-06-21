package com.soethihatun.photogallery.data.model.dto.response

import com.google.gson.annotations.SerializedName

data class SearchPhotosResponse(
    @SerializedName("total")
    val total: Int? = null,

    @SerializedName("total_pages")
    val totalPages: Int? = null,

    @SerializedName("results")
    val results: List<PhotosResponse?>? = null
)
