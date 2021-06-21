package com.soethihatun.photogallery.data.repository.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.soethihatun.photogallery.data.model.entity.PhotoEntity

@Database(entities = [PhotoEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photosDao(): PhotosDao
}
