package com.example.navigationdrawerdemo.repository

import com.example.navigationdrawerdemo.api.ApiService
import com.example.navigationdrawerdemo.api.NetworkCall
import com.example.navigationdrawerdemo.repository.models.Device
import javax.inject.Inject

class DevicesRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getDevices(): Result<List<Device>> {
        return NetworkCall.safeApiCall { apiService.getDevices().devices }
    }
}