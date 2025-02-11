package com.example.model

import com.example.config.UUIDSerializer
import com.example.config.jsonConfig
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.json.jsonb
import java.util.UUID

object Texts : Table() {
    val id = uuid("id")
    val title = text("title")
    val metadata = text("metadata")
    @OptIn(ExperimentalSerializationApi::class)
    val pageIds = jsonb<List<UUID>>("page_ids", jsonConfig, ListSerializer(UUIDSerializer))
    override val primaryKey = PrimaryKey(id)
}
