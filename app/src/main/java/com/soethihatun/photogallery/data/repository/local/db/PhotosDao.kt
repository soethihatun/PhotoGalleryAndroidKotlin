package com.soethihatun.photogallery.data.repository.local.db

import androidx.room.*
import com.soethihatun.photogallery.data.model.entity.PhotoEntity

@Dao
interface PhotosDao {
    @Query("SELECT * FROM photos")
    suspend fun getPhotos(): List<PhotoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotos(photos: List<PhotoEntity>)

    @Query("DELETE FROM photos")
    suspend fun deletePhotos()
}
