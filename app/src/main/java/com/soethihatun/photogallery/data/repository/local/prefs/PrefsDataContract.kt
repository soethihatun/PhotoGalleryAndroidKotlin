package com.soethihatun.photogallery.data.repository.local.prefs

interface PrefsDataContract {
    fun getLastUpdatedPhotosTime(): Long

    fun setLastUpdatedPhotosTime(updatedTime: Long)
}
