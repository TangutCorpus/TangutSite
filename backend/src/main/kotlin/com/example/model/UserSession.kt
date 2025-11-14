package com.example.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.Instant
import java.util.*

@Serializable
data class UserSession(
    @Contextual val sessionId: UUID,
    @Contextual val userId: UUID,
    val role: UserRoles = UserRoles.EDITOR,
    val date: Long = Instant.now().toEpochMilli(),
)
