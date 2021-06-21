package com.soethihatun.photogallery.data.repository.local.db

import com.soethihatun.photogallery.data.model.entity.PhotoEntity
import com.soethihatun.photogallery.util.RandomFactory

object PhotoFactory {
    fun makePhotoEntity() = PhotoEntity(
        id = RandomFactory.makeString(),
        photoUrl = RandomFactory.makeString(),
        userName = RandomFactory.makeString(),
        description = RandomFactory.makeString()
    )
}
