package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Reconstruction(
    val id: String,
    val author: String,
    val year: Int,
    val value: String,
    val deprecated: Boolean
)