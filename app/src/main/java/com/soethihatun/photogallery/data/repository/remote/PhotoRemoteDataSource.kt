package com.soethihatun.photogallery.data.repository.remote

import com.soethihatun.photogallery.core.exception.Failure
import com.soethihatun.photogallery.core.functional.Either
import com.soethihatun.photogallery.data.model.domain.Photo
import com.soethihatun.photogallery.data.model.dto.response.PhotosResponse
import com.soethihatun.photogallery.data.model.mapper.Mapper
import com.soethihatun.photogallery.data.repository.PhotoDataContract
import com.soethihatun.photogallery.data.repository.remote.api.PhotoService
import com.soethihatun.photogallery.util.interceptor.NetworkException
import timber.log.Timber
import javax.inject.Inject

class PhotoRemoteDataSource @Inject constructor(
    private val photoService: PhotoService,
    private val photoMapper: Mapper<PhotosResponse, Photo>
) : PhotoDataContract.RemoteDataSource {
    override suspend fun fetchPhotos(clientId: String): Either<Failure, List<Photo>> = try {
        photoService.fetchPhotos(clientId).let { response ->
            val data = response?.filterNotNull()?.map {
                photoMapper.map(it)
            } ?: emptyList()
            Either.Right(data)
        }
    } catch (e: NetworkException) {
        Timber.e(e)
        Either.Left(Failure.NetworkConnection)
    } catch (e: Throwable) {
        Timber.e(e)
        Either.Left(Failure.ServerError)
    }

    override suspend fun searchPhotos(
        query: String,
        clientId: String
    ): Either<Failure, List<Photo>> = try {
        photoService.searchPhotos(query, clientId).let { response ->
            val data = response?.results?.filterNotNull()?.map {
                photoMapper.map(it)
            } ?: emptyList()
            Either.Right(data)
        }
    } catch (e: NetworkException) {
        Timber.e(e)
        Either.Left(Failure.NetworkConnection)
    } catch (e: Throwable) {
        Timber.e(e)
        Either.Left(Failure.ServerError)
    }
}
