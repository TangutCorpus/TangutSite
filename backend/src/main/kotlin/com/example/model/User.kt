package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class User(val name: String, val age: Int)