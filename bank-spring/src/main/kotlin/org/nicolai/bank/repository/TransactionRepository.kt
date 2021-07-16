package org.nicolai.bank.repository

import org.nicolai.bank.dto.TransactionDto
import org.nicolai.bank.dto.TransactionDtoRes
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import kotlin.jvm.Throws

@Repository
@Transactional
class TransactionRepository(val db: JdbcTemplate) {

    fun getAll(): List<TransactionDto> {

        val sql = "Select * from Transaction"
        val list = db.query(sql, BeanPropertyRowMapper(TransactionDto::class.java))

        return list
    }

    @Transactional(rollbackFor = [SqlException::class, ])
    @Throws(SqlException::class)
    fun addOne(transactionDto: TransactionDto): TransactionDto {
        val sqlInsert = "insert into Transaction (sender, receiver, date, amount) values (?, ?, NOW(), ?)"
        val sqlSender = "update Account set Balance = Balance - ? where id = ? and Balance > ?"
        val sqlReceiver = "update Account set Balance = Balance + ? where id = ?"


        try {
            val res = db.update(sqlSender, transactionDto.amount, transactionDto.sender, transactionDto.amount)
            if (res > 0) {
                val res2 = db.update(sqlReceiver, transactionDto.amount, transactionDto.receiver)
                if (res2 == 0) {
                    println("no receiver")
                    throw Exception("No account found")
                }
                val res3 = db.update(sqlInsert, /*BeanPropertyRowMapper(TransactionDto::class.java)*/
                    transactionDto.sender, transactionDto.receiver, transactionDto.amount)
                if (res3 == 0) {
                    throw Exception("No transaction")
                }
                val id = db.query("select max(id) as ret from Transaction", Util.intRowMapper)
                transactionDto.id = id[0]
                transactionDto.date = LocalDateTime.now()
                return transactionDto
            } else {
                throw Exception("SQL ERROR")
            }
        } catch (e: Exception) {
            println(e.message)
            throw SqlException("Fang denne")
        }
    }

    fun getByUser(id: Int): Map<String, Any> {
        val sqlUsername = "select username from user, account where account.id = ? and account.owner = user.id"
        val sqlSend = "select * from transactionview where sender = ?"
        val sqlRec = "select * from transactionview where receiver = ?"

        val username = db.query(sqlUsername, BeanPropertyRowMapper(String::class.java), id)
        val sent = db.query(sqlSend, BeanPropertyRowMapper(TransactionDtoRes::class.java), id)
        val rec = db.query(sqlRec, BeanPropertyRowMapper(TransactionDtoRes::class.java), id)

        val map = HashMap<String, Any>()
        map["username"] = username
        map["sent"] = sent
        map["rec"] = rec

        return map
    }

}
