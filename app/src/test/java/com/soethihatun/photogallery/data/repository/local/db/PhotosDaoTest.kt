package com.soethihatun.photogallery.data.repository.local.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.soethihatun.photogallery.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class PhotosDaoTest {

    private lateinit var database: AppDatabase

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun whenInsertPhotosAndGetPhotos_retrievesCorrectEntity() = runBlockingTest {
        // GIVEN - insert data
        val photo = PhotoFactory.makePhotoEntity()
        database.photosDao().insertPhotos(listOf(photo))

        // WHEN - Get data from the database
        val photos = database.photosDao().getPhotos()

        // THEN - There is only 1 record in the database, and contains the expected values
        MatcherAssert.assertThat(photos.size, CoreMatchers.`is`(1))
        MatcherAssert.assertThat(photos[0].id, CoreMatchers.`is`(photo.id))
        MatcherAssert.assertThat(photos[0].photoUrl, CoreMatchers.`is`(photo.photoUrl))
        MatcherAssert.assertThat(photos[0].userName, CoreMatchers.`is`(photo.userName))
        MatcherAssert.assertThat(photos[0].description, CoreMatchers.`is`(photo.description))
    }

    @Test
    fun whenInsertPhotoWithExistingId_replacesOnConflict() = runBlockingTest {
        // GIVEN - insert data
        val photo1 = PhotoFactory.makePhotoEntity()
        database.photosDao().insertPhotos(listOf(photo1))

        // WHEN - the data with the same id is inserted
        val photo2 = PhotoFactory.makePhotoEntity()
        val newPhoto = photo1.copy(photoUrl = photo2.photoUrl, userName = photo2.photoUrl, description = photo2.photoUrl)
        database.photosDao().insertPhotos(listOf(newPhoto))

        // THEN - The loaded data contains the expected values
        val photos = database.photosDao().getPhotos()
        MatcherAssert.assertThat(photos.size, CoreMatchers.`is`(1))
        MatcherAssert.assertThat(photos[0].id, CoreMatchers.`is`(newPhoto.id))
        MatcherAssert.assertThat(photos[0].photoUrl, CoreMatchers.`is`(newPhoto.photoUrl))
        MatcherAssert.assertThat(photos[0].userName, CoreMatchers.`is`(newPhoto.userName))
        MatcherAssert.assertThat(photos[0].description, CoreMatchers.`is`(newPhoto.description))
    }

    @Test
    fun whenDeletePhotosAndGettingPhotos_retrievesEmpty() = runBlockingTest {
        // GIVE - a data inserted
        val photo1 = PhotoFactory.makePhotoEntity()
        val photo2 = PhotoFactory.makePhotoEntity()
        database.photosDao().insertPhotos(listOf(photo1, photo2))

        // WHEN - deleting all data
        database.photosDao().deletePhotos()

        // THEN - The list is empty
        val photos = database.photosDao().getPhotos()
        MatcherAssert.assertThat(photos.isEmpty(), CoreMatchers.`is`(true))
    }
}
