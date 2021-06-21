package com.soethihatun.photogallery.data.model.mapper

import com.soethihatun.photogallery.data.model.domain.Photo
import com.soethihatun.photogallery.data.model.entity.PhotoEntity

class PhotoToEntityMapper : Mapper<Photo, PhotoEntity> {
    override fun map(input: Photo): PhotoEntity {
        return PhotoEntity(
            id = input.id,
            photoUrl = input.photoUrl,
            userName = input.userName,
            description = input.description
        )
    }
}
