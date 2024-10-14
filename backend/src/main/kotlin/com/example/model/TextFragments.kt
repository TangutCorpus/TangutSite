package com.example.model

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object TextFragments : Table() {
    val id = integer("id").autoIncrement()
    val textId = integer("textId") //TODO: Make it a foreign key after the merging of all branches.
    val lineNumber = integer("lineNumber")
    val content = text("content")
    val comment = text("comment")
    val createdAt = datetime("created_at").nullable()
    override val primaryKey = PrimaryKey(id)
}