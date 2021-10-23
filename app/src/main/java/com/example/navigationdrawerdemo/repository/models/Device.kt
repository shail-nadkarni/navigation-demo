package com.example.navigationdrawerdemo.repository.models

data class Device(
    val id: String,
    val type: String,
    val price: Int,
    val currency: String,
    val isFavorite: Boolean,
    val imageUrl: String,
    val title: String,
    val description: String
)
