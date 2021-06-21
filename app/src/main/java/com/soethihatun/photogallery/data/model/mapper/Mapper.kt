package com.soethihatun.photogallery.data.model.mapper

interface Mapper<I, O> {
    fun map(input: I): O
}
