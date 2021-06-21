package com.soethihatun.photogallery.features.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.soethihatun.photogallery.core.exception.Failure
import com.soethihatun.photogallery.core.platform.Event
import com.soethihatun.photogallery.data.model.domain.Photo
import com.soethihatun.photogallery.data.repository.PhotoDataContract
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(
    private val repository: PhotoDataContract.Repository
) : ViewModel() {

    private val _photos = MutableLiveData<List<Photo>>(emptyList())
    val photos: LiveData<List<Photo>> = _photos

    private val _dataLoading = MutableLiveData<Boolean>(false)
    val dataLoading: LiveData<Boolean> = _dataLoading

    val isEmpty: LiveData<Boolean> = Transformations.map(_photos) { it.isEmpty() }

    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> = _message

    private val _spanCount = MutableLiveData<Int>(1)
    val spanCount: LiveData<Int> = _spanCount

    private val _openPhotoEvent = MutableLiveData<Event<Photo>>()
    val openPhotoEvent: LiveData<Event<Photo>> = _openPhotoEvent

    private val _openSearchPhotoEvent = MutableLiveData<Event<Unit>>()
    val openSearchPhotoEvent: LiveData<Event<Unit>> = _openSearchPhotoEvent

    init {
        loadPhotos(forceUpdate = false)
    }

    private fun loadPhotos(forceUpdate: Boolean) {
        viewModelScope.launch {
            _dataLoading.value = true
            val either = repository.loadPhotos(forceUpdate)
            either.fold(::handleFailure, ::handlePhotos)
            _dataLoading.value = false
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

    fun openSearchPhoto() {
        _openSearchPhotoEvent.value = Event(Unit)
    }

    fun setListView() {
        _spanCount.value = 1
    }

    fun setGridView() {
        _spanCount.value = 2
    }

    fun refresh() {
        loadPhotos(forceUpdate = true)
    }
}
