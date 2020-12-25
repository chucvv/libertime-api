package vn.com.libertime.database.table

import org.jetbrains.exposed.sql.Expression
import org.jetbrains.exposed.sql.ExpressionWithColumnType
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.QueryBuilder
import org.postgis.PGbox2d

internal infix fun ExpressionWithColumnType<*>.within(box: PGbox2d): Op<Boolean> = WithinOp(this, box)

private class WithinOp(val expr1: Expression<*>, val box: PGbox2d) : Op<Boolean>() {
    override fun toQueryBuilder(queryBuilder: QueryBuilder) {
        "${expr1.toQueryBuilder(queryBuilder)} && ST_MakeEnvelope(${box.llb.x}, ${box.llb.y}, ${box.urt.x}, ${box.urt.y}, 4326)"
    }
}