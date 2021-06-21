package com.soethihatun.photogallery.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class PhotoEntity(
    @PrimaryKey val id: String,
    val photoUrl: String,
    val userName: String,
    val description: String
)
