package com.soethihatun.photogallery.features.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.soethihatun.photogallery.core.exception.Failure
import com.soethihatun.photogallery.core.platform.Event
import com.soethihatun.photogallery.data.model.domain.Photo
import com.soethihatun.photogallery.data.repository.PhotoDataContract
import kotlinx.coroutines.launch

class SearchPhotoViewModel @ViewModelInject constructor(
    private val repository: PhotoDataContract.Repository
) : ViewModel() {

    private val _photos = MutableLiveData<List<Photo>>(emptyList())
    val photos: LiveData<List<Photo>> = _photos

    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> = _message

    private val _openPhotoEvent = MutableLiveData<Event<Photo>>()
    val openPhotoEvent: LiveData<Event<Photo>> = _openPhotoEvent

    fun resetSearchPhotos() {
        _photos.value = emptyList()
    }

    fun searchPhotos(query: String) {
        viewModelScope.launch {
            val either = repository.searchPhotos(query)
            either.fold(::handleFailure, ::handlePhotos)
        }
    }

    private fun handlePhotos(photos: List<Photo>) {
        _photos.value = photos
    }

    private fun handleFailure(failure: Failure) {
        when (failure) {
            Failure.NetworkConnection -> {
                _message.value = Event("No connection")
            }
            Failure.ServerError -> {
                _message.value = Event("Something went wrong with the server")
            }
            else -> {
                _message.value = Event("Something went wrong")
            }
        }
    }

    fun openPhoto(photo: Photo) {
        _openPhotoEvent.value = Event(photo)
    }
}
