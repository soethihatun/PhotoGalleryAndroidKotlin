package com.soethihatun.photogallery.data.repository

import com.soethihatun.photogallery.BuildConfig
import com.soethihatun.photogallery.core.exception.Failure
import com.soethihatun.photogallery.core.functional.Either
import com.soethihatun.photogallery.data.model.Constants
import com.soethihatun.photogallery.data.model.domain.Photo
import com.soethihatun.photogallery.util.DateTimeUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    private val localDataSource: PhotoDataContract.LocalDataSource,
    private val remoteDataSource: PhotoDataContract.RemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : PhotoDataContract.Repository {
    override suspend fun loadPhotos(forceUpdate: Boolean): Either<Failure, List<Photo>> =
        withContext(ioDispatcher) {
            if (isUpdateRequired(forceUpdate)) {
                refreshPhotos()
            } else {
                when (val either = localDataSource.getPhotos()) {
                    is Either.Left -> refreshPhotos()
                    is Either.Right -> {
                        if (either.b.isEmpty()) {
                            refreshPhotos()
                        } else {
                            either
                        }
                    }
                }
            }
        }

    override suspend fun searchPhotos(query: String): Either<Failure, List<Photo>> =
        withContext(ioDispatcher) {
            remoteDataSource.searchPhotos(query, BuildConfig.UNSPLASH_ACCESS_KEY)
        }

    private suspend fun refreshPhotos(): Either<Failure, List<Photo>> = withContext(ioDispatcher) {
        remoteDataSource.fetchPhotos(BuildConfig.UNSPLASH_ACCESS_KEY)
            .also {
                if (it is Either.Right) {
                    clearAndSavePhotos(it.b)
                }
            }
    }

    private suspend fun clearAndSavePhotos(photos: List<Photo>) = withContext(ioDispatcher) {
        localDataSource.deleteAllPhotos()
        localDataSource.savePhotos(photos)
        localDataSource.setLastUpdatedPhotosTime(Calendar.getInstance().timeInMillis)
    }

    private fun isUpdateRequired(forceUpdate: Boolean): Boolean = forceUpdate || isDataExpired()

    private fun isDataExpired(): Boolean {
        val last = localDataSource.getLastUpdatedPhotosTime()
        return !DateTimeUtils.isWithin(
            lastTimeInMillis = last,
            durationInMin = Constants.PHOTO_LIFETIME_DURATION_IN_MIN
        )
    }
}
