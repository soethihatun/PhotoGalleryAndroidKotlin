package com.soethihatun.photogallery.di

import android.content.Context
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.soethihatun.photogallery.data.model.domain.Photo
import com.soethihatun.photogallery.data.model.dto.response.PhotosResponse
import com.soethihatun.photogallery.data.model.entity.PhotoEntity
import com.soethihatun.photogallery.data.model.mapper.EntityToPhotoMapper
import com.soethihatun.photogallery.data.model.mapper.Mapper
import com.soethihatun.photogallery.data.model.mapper.PhotoResponseMapper
import com.soethihatun.photogallery.data.model.mapper.PhotoToEntityMapper
import com.soethihatun.photogallery.data.repository.PhotoDataContract
import com.soethihatun.photogallery.data.repository.PhotoRepository
import com.soethihatun.photogallery.data.repository.local.PhotoLocalDataSource
import com.soethihatun.photogallery.data.repository.local.db.AppDatabase
import com.soethihatun.photogallery.data.repository.local.prefs.Prefs
import com.soethihatun.photogallery.data.repository.local.prefs.PrefsDataContract
import com.soethihatun.photogallery.data.repository.remote.PhotoRemoteDataSource
import com.soethihatun.photogallery.data.repository.remote.api.PhotoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun providePhotoService(retrofit: Retrofit) = retrofit.create(PhotoService::class.java)

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "App.db"
        ).build()
    }

    @Singleton
    @Provides
    fun providePhotoResponseMapper(): Mapper<PhotosResponse, Photo> {
        return PhotoResponseMapper()
    }

    @Singleton
    @Provides
    fun providePhotoToEntityMapper(): Mapper<Photo, PhotoEntity> {
        return PhotoToEntityMapper()
    }

    @Singleton
    @Provides
    fun provideEntityToPhotoMapper(): Mapper<PhotoEntity, Photo> {
        return EntityToPhotoMapper()
    }

    @Provides
    @Singleton
    fun providesPrefs(@ApplicationContext context: Context): PrefsDataContract {
        return Prefs(PreferenceManager.getDefaultSharedPreferences(context))
    }

    @Singleton
    @Provides
    fun providePhotoLocalDataSource(
        database: AppDatabase,
        prefs: PrefsDataContract,
        photoToEntityMapper: Mapper<Photo, PhotoEntity>,
        entityToPhotoMapper: Mapper<PhotoEntity, Photo>
    ): PhotoDataContract.LocalDataSource =
        PhotoLocalDataSource(database.photosDao(), prefs, photoToEntityMapper, entityToPhotoMapper)

    @Singleton
    @Provides
    fun providePhotoRemoteDataSource(
        photoService: PhotoService,
        mapper: Mapper<PhotosResponse, Photo>
    ): PhotoDataContract.RemoteDataSource = PhotoRemoteDataSource(photoService, mapper)

    @Singleton
    @Provides
    fun providePhotoRepository(
        localDataSource: PhotoDataContract.LocalDataSource,
        remoteDataSource: PhotoDataContract.RemoteDataSource,
        ioDispatcher: CoroutineDispatcher
    ): PhotoDataContract.Repository =
        PhotoRepository(localDataSource, remoteDataSource, ioDispatcher)
}
