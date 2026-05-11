package com.example.model

import kotlinx.serialization.Serializable

@Serializable
enum class ComponentRole {
    semantic, phonetic, chinesePhonetic, other
}