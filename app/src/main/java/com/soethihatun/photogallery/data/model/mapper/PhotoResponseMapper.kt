package com.soethihatun.photogallery.data.model.mapper

import com.soethihatun.photogallery.data.model.domain.Photo
import com.soethihatun.photogallery.data.model.dto.response.PhotosResponse

class PhotoResponseMapper : Mapper<PhotosResponse, Photo> {
    override fun map(input: PhotosResponse): Photo {
        return Photo(
            id = input.id ?: "",
            photoUrl = input.urls?.small ?: "",
            userName = input.user?.name ?: "",
            description = input.description ?: ""
        )
    }
}
