package com.example.navigationdrawerdemo.ui

import com.example.navigationdrawerdemo.ui.devices.DeviceUiModel

sealed class UiState {
    object Loading : UiState()
    data class Error(val message: String) : UiState()
    data class Success(val devices: List<DeviceUiModel>) : UiState()
}
