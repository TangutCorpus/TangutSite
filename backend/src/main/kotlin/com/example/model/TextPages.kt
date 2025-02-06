package com.example.model

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object TextPages : Table() {
    val id = uuid("id")
    val textId = uuid("textId").references(Texts.id, onDelete = ReferenceOption.CASCADE)
    val imagesIDs = text("imagesIDs")
    val pageNumber = integer("pageNumber")
    val pureText = text("pure_text")
    val glossedTextXML = text("glossedTextXML")
    val translationsXML = text("translationsXML")
    override val primaryKey = PrimaryKey(id)
}
