package com.nimble.assessment.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nimble.assessment.repository.NimbleRepository
import com.nimble.assessment.repository.entities.Response
import kotlinx.coroutines.launch

/**
 * View Model used for Main Activity (Home Screen)
 */
class MainViewModel(private val repository: NimbleRepository): ViewModel() {
    private val _liveData = MutableLiveData<List<Response.SimplePharmacy>>()

    val liveData: LiveData<List<Response.SimplePharmacy>>
        get() = _liveData

    fun loadPharmacyList() {
        viewModelScope.launch {
            val result = repository.loadPharmacyListFromJson()
            _liveData.value = result
        }
    }
}