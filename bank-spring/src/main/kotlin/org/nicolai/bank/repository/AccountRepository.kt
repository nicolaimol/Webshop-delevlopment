package org.nicolai.bank.repository

import org.nicolai.bank.dto.AccountDto
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class AccountRepository(val db: JdbcTemplate) {

    fun getAll(): List<AccountDto> {
        val sql = "select * from account"
        val res = db.query(sql, BeanPropertyRowMapper(AccountDto::class.java))

        return res
    }

    fun addOne(accountDto: AccountDto): AccountDto {
        val sql = "insert into account (type, balance, owner) values(?, ?, ?)"
        try {
            var res = db.update(sql, accountDto.type, accountDto.balance, accountDto.owner)
            var id = db.query("select max(id) as ret from Account", Util.intRowMapper)
            accountDto.id = id[0]
            return accountDto

        } catch (e: Exception) {
            println(e.message)
            return AccountDto()
        }
    }

    fun getOne(id: Int): AccountDto {
        val sql = "select * from Account where id = ?"
        val res = db.query(sql, BeanPropertyRowMapper(AccountDto::class.java), id)

        if (res.size > 0) {
            return res[0]
        } else {
            return AccountDto()
        }
    }

    fun getByUser(id: Int): List<AccountDto> {
        val sql = "select * from Account where Owner = ?"
        val res = db.query(sql, BeanPropertyRowMapper(AccountDto::class.java), id)

        return res
    }

    fun updateOne(id: Int, accountDto: AccountDto): AccountDto {

        val sql = "update Account set type = ?, balance = ? where id = ?"
        val res = db.update(sql, accountDto.type, accountDto.balance, id)

        if (res == 1) {
            accountDto.id = id
            return accountDto
        } else {
            return AccountDto()
        }
    }

    fun deleteOne(id: Int): AccountDto {
        val sql = "delete from Account where id = ?"
        val res = db.query(sql, BeanPropertyRowMapper(AccountDto::class.java), id)

        if (res.size > 0) {
            return res[0]
        } else {
            return AccountDto()
        }
    }

}
