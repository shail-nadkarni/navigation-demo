package com.example.navigationdrawerdemo.repository

sealed class Result<out T> {
    data class Success<out T>(val value: T): Result<T>()
    data class Error(val error: Exception? = null): Result<Nothing>()
}
