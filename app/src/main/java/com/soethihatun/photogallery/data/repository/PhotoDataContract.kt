package com.soethihatun.photogallery.data.repository

import com.soethihatun.photogallery.core.exception.Failure
import com.soethihatun.photogallery.core.functional.Either
import com.soethihatun.photogallery.data.model.domain.Photo

interface PhotoDataContract {
    interface Repository {
        suspend fun loadPhotos(forceUpdate: Boolean = false): Either<Failure, List<Photo>>

        suspend fun searchPhotos(query: String): Either<Failure, List<Photo>>
    }

    interface LocalDataSource {
        suspend fun getPhotos(): Either<Failure, List<Photo>>

        suspend fun savePhotos(photos: List<Photo>)

        suspend fun deleteAllPhotos()

        fun getLastUpdatedPhotosTime(): Long

        fun setLastUpdatedPhotosTime(updatedTime: Long)
    }

    interface RemoteDataSource {
        suspend fun fetchPhotos(clientId: String): Either<Failure, List<Photo>>

        suspend fun searchPhotos(query: String, clientId: String): Either<Failure, List<Photo>>
    }
}
