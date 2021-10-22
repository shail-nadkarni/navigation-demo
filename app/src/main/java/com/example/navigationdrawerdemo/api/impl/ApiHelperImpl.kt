package com.example.navigationdrawerdemo.api.impl

import com.example.navigationdrawerdemo.api.ApiHelper
import com.example.navigationdrawerdemo.api.ApiService
import com.example.navigationdrawerdemo.models.DevicesResponse
import javax.inject.Inject
import retrofit2.Response

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
) : ApiHelper {
    override suspend fun getDevices(): Response<DevicesResponse> = apiService.getDevices()
}