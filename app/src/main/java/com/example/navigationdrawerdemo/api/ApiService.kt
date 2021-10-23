package com.example.navigationdrawerdemo.api

import com.example.navigationdrawerdemo.repository.models.DevicesResponse
import retrofit2.http.GET

interface ApiService {
    @GET("v1/devices")
    suspend fun getDevices(): DevicesResponse
}