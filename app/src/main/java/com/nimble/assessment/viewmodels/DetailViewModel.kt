package com.nimble.assessment.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nimble.assessment.repository.NimbleRepository
import com.nimble.assessment.repository.entities.DataResult
import com.nimble.assessment.repository.entities.LoadResult
import com.nimble.assessment.repository.entities.Response
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: NimbleRepository): ViewModel() {

    private val _pharmacyInfo = MutableLiveData<Response.PharmacyDetail>()
    val pharmacyInfo: LiveData<Response.PharmacyDetail>
        get() = _pharmacyInfo

    private val _loading = MutableLiveData<LoadResult>()
    val loading: LiveData<LoadResult>
        get() = _loading

    fun loadPharmacy(pharmacyId: String) {
        viewModelScope.launch {
            _loading.value = LoadResult.LOADING
            when (val result = repository.fetchPharmacyDetail(pharmacyId)) {
                is DataResult.SUCCESS -> {
                    Log.w("DetailViewModel", result.data.toString())
                    _pharmacyInfo.value = result.data
                    _loading.value = LoadResult.SUCCESS
                }
                is DataResult.FAILURE -> {
                    Log.w("DetailViewModel", result.exception.toString())
                    _loading.value = LoadResult.FAILURE
                }
            }
        }
    }
}