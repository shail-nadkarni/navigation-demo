package com.example.navigationdrawerdemo.ui.devices

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigationdrawerdemo.models.DevicesResponse
import com.example.navigationdrawerdemo.repository.DevicesRepository
import com.example.navigationdrawerdemo.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class DevicesViewModel @Inject constructor(
    private val devicesRepository: DevicesRepository
    ) : ViewModel() {

    private val _res = MutableLiveData<Resource<DevicesResponse>>()

    val res : LiveData<Resource<DevicesResponse>>
        get() = _res

    fun onLoad() = viewModelScope.launch {
        _res.postValue(Resource.loading(null))
        devicesRepository.getDevices().let {
            if (it.isSuccessful){
                _res.postValue(Resource.success(it.body()))
            }else{
                _res.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }

}