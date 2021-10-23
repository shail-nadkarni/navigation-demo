package com.example.navigationdrawerdemo.api

import com.example.navigationdrawerdemo.repository.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NetworkCall {
    suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        apiCall: suspend () -> T
    ): Result<T> {
        return withContext(dispatcher) {
            try {
                var result = apiCall.invoke()
                Result.Success(result)
            } catch (exception: Exception) {
                Result.Error(exception)
            }
        }
    }
}