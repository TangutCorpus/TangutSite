package com.example.model

import com.example.config.UUIDSerializer
import com.example.config.jsonConfig
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.json.jsonb
import java.util.*

object TextPages : Table() {
    val id = uuid("id")
    val textId = uuid("textId").references(Texts.id, onDelete = ReferenceOption.CASCADE)

    @OptIn(ExperimentalSerializationApi::class)
    val imagesIDs = jsonb<List<UUID>>("image_ids", jsonConfig, ListSerializer(UUIDSerializer))
    val pageNumber = integer("pageNumber")
    val pureText = text("pure_text")
    val glossedTextXML = text("glossedTextXML")
    val translationsXML = text("translationsXML")
    override val primaryKey = PrimaryKey(id)
}
