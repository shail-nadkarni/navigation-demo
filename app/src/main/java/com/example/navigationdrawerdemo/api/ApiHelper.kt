package com.example.navigationdrawerdemo.api

import com.example.navigationdrawerdemo.models.DevicesResponse
import retrofit2.Response

interface ApiHelper {
    suspend fun getDevices(): Response<DevicesResponse>
}