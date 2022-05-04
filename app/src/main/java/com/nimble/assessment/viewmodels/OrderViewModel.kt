package com.nimble.assessment.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nimble.assessment.repository.NimbleRepository
import com.nimble.assessment.repository.entities.DataResult
import com.nimble.assessment.repository.entities.LoadResult
import com.nimble.assessment.repository.entities.Medication
import kotlinx.coroutines.launch

/**
 * [ViewModel] used for Order Page
 */
class OrderViewModel(private val repository: NimbleRepository): ViewModel() {
    private val _medicationList = MutableLiveData<List<Medication>>()
    private val _loading = MutableLiveData<LoadResult>()

    val medicationList: LiveData<List<Medication>>
        get() = _medicationList
    val loading: LiveData<LoadResult>
        get() = _loading

    /**
     * Load medication list
     */
    fun loadMedications() {
        viewModelScope.launch {
            _loading.value = LoadResult.LOADING
            repository.loadMedicationsText {
                when(it) {
                    is DataResult.SUCCESS -> {
                        val data = it.data.filter { c -> c != '\n' }.split(',').map { text -> Medication(text) }
                        _medicationList.postValue(data)
                        _loading.postValue(LoadResult.SUCCESS)
                    }
                    is DataResult.FAILURE -> {
                        _loading.postValue(LoadResult.FAILURE)
                    }
                }
            }
        }
    }
}