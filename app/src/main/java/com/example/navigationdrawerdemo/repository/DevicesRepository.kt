package com.example.navigationdrawerdemo.repository

import com.example.navigationdrawerdemo.api.ApiHelper
import javax.inject.Inject

class DevicesRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {
    suspend fun getDevices() = apiHelper.getDevices()
}