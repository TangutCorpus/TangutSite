package com.example.model

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object Texts : Table() {
    val id = uuid("id")
    val comment = text("comment")
    val lineIds = text("line_ids")
    val pureText = text("pure_text")
    val createdAt = datetime("created_at").nullable()
    override val primaryKey = PrimaryKey(id)
}
