package com.example.navigationdrawerdemo.api

import com.example.navigationdrawerdemo.models.DevicesResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("v1/devices")
    suspend fun getDevices(): Response<DevicesResponse>
}