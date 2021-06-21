package com.soethihatun.photogallery.util

import java.util.*
import java.util.concurrent.ThreadLocalRandom

object RandomFactory {
    fun makeString() = UUID.randomUUID().toString()

    fun makeInt() = ThreadLocalRandom.current().nextInt(0, 1000 + 1)

    fun makeDouble() = ThreadLocalRandom.current().nextDouble(0.0, 1000.0 + 1)

    fun makeBoolean() = ThreadLocalRandom.current().nextBoolean()
}
