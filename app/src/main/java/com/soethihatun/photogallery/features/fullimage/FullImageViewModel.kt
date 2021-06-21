package com.soethihatun.photogallery.features.fullimage

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*

class FullImageViewModel @ViewModelInject constructor() : ViewModel() {
    private val _photoUrl = MutableLiveData<String>("")
    val photoUrl: LiveData<String> = _photoUrl

    fun init(photoUrl: String) {
        _photoUrl.value = photoUrl
    }
}
