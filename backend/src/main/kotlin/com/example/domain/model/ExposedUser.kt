package com.example.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ExposedUser(val name: String, val age: Int)