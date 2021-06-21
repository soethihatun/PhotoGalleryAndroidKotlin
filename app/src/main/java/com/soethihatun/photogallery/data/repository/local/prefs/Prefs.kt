package com.soethihatun.photogallery.data.repository.local.prefs

import android.annotation.SuppressLint
import android.content.SharedPreferences

class Prefs(private val prefs: SharedPreferences) : PrefsDataContract {

    private val lastUpdatedPhotosTime = "LAST_UPDATED_PHOTOS_TIME"

    override fun getLastUpdatedPhotosTime(): Long {
        return prefs.getLong(lastUpdatedPhotosTime, -1L)
    }

    @SuppressLint("ApplySharedPref") // because we need it immediately
    override fun setLastUpdatedPhotosTime(updatedTime: Long) {
        prefs.edit().putLong(lastUpdatedPhotosTime, updatedTime).commit()
    }
}
