package com.example.model

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object TextFragments : Table() {
    val id = integer("id").autoIncrement()
    val textId = integer("textId").references(Texts.id, onDelete = ReferenceOption.CASCADE)
    val lineNumber = integer("lineNumber")
    val contentXML = text("contentXML")
    val commentXML = text("commentXML")
    val createdAt = datetime("created_at").nullable()
    override val primaryKey = PrimaryKey(id)
}
