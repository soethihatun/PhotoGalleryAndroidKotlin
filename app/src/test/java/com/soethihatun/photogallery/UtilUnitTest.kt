package com.soethihatun.photogallery

import com.soethihatun.photogallery.util.DateTimeUtils
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class UtilUnitTest {
    @Test
    fun isWithin_nowIn15Min_isCorrect() {
        val now = Calendar.getInstance()
        assertTrue(DateTimeUtils.isWithin(now.timeInMillis, 15))
    }

    @Test
    fun isWithin_yesterdayIn15Min_isIncorrect() {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -1)
        assertFalse(DateTimeUtils.isWithin(cal.timeInMillis, 15))
    }
}
