package com.soethihatun.photogallery.data.repository.local

import com.soethihatun.photogallery.core.exception.Failure
import com.soethihatun.photogallery.core.functional.Either
import com.soethihatun.photogallery.data.model.domain.Photo
import com.soethihatun.photogallery.data.model.entity.PhotoEntity
import com.soethihatun.photogallery.data.model.mapper.Mapper
import com.soethihatun.photogallery.data.repository.PhotoDataContract
import com.soethihatun.photogallery.data.repository.local.db.PhotosDao
import com.soethihatun.photogallery.data.repository.local.prefs.PrefsDataContract
import timber.log.Timber
import javax.inject.Inject

class PhotoLocalDataSource @Inject constructor(
    private val photosDao: PhotosDao,
    private val prefs: PrefsDataContract,
    private val photoToEntityMapper: Mapper<Photo, PhotoEntity>,
    private val entityToPhotoMapper: Mapper<PhotoEntity, Photo>
) : PhotoDataContract.LocalDataSource {
    override suspend fun getPhotos(): Either<Failure, List<Photo>> = try {
        val photos = photosDao.getPhotos().map { entityToPhotoMapper.map(it) }
        Either.Right(photos)
    } catch (e: Throwable) {
        Timber.e(e)
        Either.Left(Failure.DbError)
    }

    override suspend fun savePhotos(photos: List<Photo>) {
        val entities = photos.map { photoToEntityMapper.map(it) }
        photosDao.insertPhotos(entities)
    }

    override suspend fun deleteAllPhotos() {
        photosDao.deletePhotos()
    }

    override fun getLastUpdatedPhotosTime(): Long = prefs.getLastUpdatedPhotosTime()

    override fun setLastUpdatedPhotosTime(updatedTime: Long) {
        prefs.setLastUpdatedPhotosTime(updatedTime)
    }
}
