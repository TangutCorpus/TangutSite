package com.example.utils

import cz.jirutka.rsql.parser.ast.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.neq
import org.jetbrains.exposed.sql.stringParam
import java.util.*

class CustomIlikeOp(val left: Expression<*>, val right: Expression<*>) : Op<Boolean>() {
    override fun toQueryBuilder(queryBuilder: QueryBuilder) {
        queryBuilder.append(left).append(" ILIKE ").append(right)
    }
}

class CustomJsonbIlikeOp(val col: Column<*>, val pattern: String) : Op<Boolean>() {
    override fun toQueryBuilder(queryBuilder: QueryBuilder) {
        queryBuilder.append("(").append(col).append("::text) ILIKE ")
        queryBuilder.registerArgument(VarCharColumnType(), pattern)
    }
}

abstract class BaseRSQLVisitor : NoArgRSQLVisitorAdapter<Op<Boolean>>() {
    override fun visit(node: AndNode): Op<Boolean> =
        node.children.map { it.accept(this) }.reduce { acc, op -> acc and op }

    override fun visit(node: OrNode): Op<Boolean> =
        node.children.map { it.accept(this) }.reduce { acc, op -> acc or op }

    protected fun applyUUIDComparison(col: Column<UUID>, op: ComparisonOperator, arg: String): Op<Boolean> {
        val uuid = arg.toUUIDOrNull() ?: throw IllegalArgumentException("Invalid UUID")
        return if (op.symbol == "==") col eq uuid else col neq uuid
    }

    protected fun applyTextComparison(exp: Expression<String>, op: ComparisonOperator, arg: String): Op<Boolean> {
        return when (op.symbol) {
            "==" -> exp eq stringParam(arg)
            "=in=" -> CustomIlikeOp(exp, stringParam("%$arg%"))
            else -> exp neq stringParam(arg)
        }
    }
}

fun String.isRsql(): Boolean =
    this.contains("==") || this.contains("=in=") || this.contains("!=") || this.contains(";") || this.contains(",")