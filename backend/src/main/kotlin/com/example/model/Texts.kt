package com.example.model

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object Texts : Table() {
    val id = uuid("id")
    val title = text("title")
    val comment = text("comment")
    val lineIds = text("line_ids")
    override val primaryKey = PrimaryKey(id)
}
