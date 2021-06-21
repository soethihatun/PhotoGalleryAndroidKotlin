package com.soethihatun.photogallery.data.repository.remote.api

import com.soethihatun.photogallery.data.model.dto.response.PhotosResponse
import com.soethihatun.photogallery.data.model.dto.response.SearchPhotosResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoService {
    @GET("photos")
    suspend fun fetchPhotos(
        @Query("client_id") clientId: String
    ): List<PhotosResponse?>?

    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("client_id") clientId: String
    ): SearchPhotosResponse?
}
