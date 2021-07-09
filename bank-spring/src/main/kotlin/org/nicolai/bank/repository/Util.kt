package org.nicolai.bank.repository

import org.nicolai.bank.dto.TransactionDto
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.time.LocalDateTime

object Util {
    fun convertToObject(vararg objects: Any?): Array<Any> {
        return arrayOf(objects)
    }

    val intRowMapper = RowMapper<Int> { rs: ResultSet, rowNum: Int ->
        val res = rs.getInt("ret")

        res
    }

    val transactionRowMapper = RowMapper<TransactionDto> { rs: ResultSet, rowNum: Int ->
        val res = TransactionDto(rs.getInt("sender"), rs.getInt("receiver"),
            rs.getObject("date", LocalDateTime::class.java), rs.getDouble("amount"))

        res

    }

}
