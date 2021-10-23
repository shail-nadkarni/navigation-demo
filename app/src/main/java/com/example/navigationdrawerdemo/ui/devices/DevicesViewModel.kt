package com.example.navigationdrawerdemo.ui.devices

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigationdrawerdemo.repository.DevicesRepository
import com.example.navigationdrawerdemo.repository.Result
import com.example.navigationdrawerdemo.repository.models.Device
import com.example.navigationdrawerdemo.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.launch

@HiltViewModel
class DevicesViewModel @Inject constructor(
    private val devicesRepository: DevicesRepository
) : ViewModel() {

    val uiState = MutableLiveData<UiState>(UiState.Loading)

    fun onLoad() = viewModelScope.launch {
        val result = devicesRepository.getDevices()
        uiState.value = when (result) {
            is Result.Error -> UiState.Error(result.error.toString())
            is Result.Success -> UiState.Success(result.value.toUiModel())
        }
    }
}

@Parcelize
data class DeviceUiModel(
    val id: String,
    val type: String,
    val price: Int,
    val currency: String,
    val isFavorite: Boolean,
    val imageUrl: String,
    val title: String,
    val description: String
) : Parcelable

fun Device.toUiModel() = DeviceUiModel(
    id = id,
    type = type,
    price = price,
    currency = currency,
    isFavorite = isFavorite,
    imageUrl = imageUrl,
    title = title,
    description = description
)

fun List<Device>.toUiModel() = map { it.toUiModel() }