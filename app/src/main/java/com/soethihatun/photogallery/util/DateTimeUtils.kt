package com.soethihatun.photogallery.util

import java.util.*

object DateTimeUtils {
    fun isWithin(lastTimeInMillis: Long, durationInMin: Int): Boolean {
        if (lastTimeInMillis == -1L) return false

        val now = Calendar.getInstance()
        val seconds: Long = (now.timeInMillis - lastTimeInMillis) / 1000
        val minutes = (seconds / 60).toInt()
        return minutes < durationInMin
    }
}
