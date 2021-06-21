package com.soethihatun.photogallery.data.model.mapper

import com.soethihatun.photogallery.data.model.domain.Photo
import com.soethihatun.photogallery.data.model.entity.PhotoEntity

class EntityToPhotoMapper : Mapper<PhotoEntity, Photo> {
    override fun map(input: PhotoEntity): Photo {
        return Photo(
            id = input.id,
            photoUrl = input.photoUrl,
            userName = input.userName,
            description = input.description
        )
    }
}
