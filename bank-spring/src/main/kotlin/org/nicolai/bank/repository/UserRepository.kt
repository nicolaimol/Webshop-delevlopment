package org.nicolai.bank.repository

import org.nicolai.bank.dto.UserDto
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.ResultSet

@Repository
@Transactional
class UserRepository(val db: JdbcTemplate, val passwordEncoder: PasswordEncoder) {

    fun getAll(): List<UserDto> {
        val sql = "select * from user"
        var list = db.query(sql, BeanPropertyRowMapper(UserDto::class.java))

        return list
    }

    fun addUser(user: UserDto): UserDto?{
        val SQL = "INSERT INTO USER(username, password, email, phone, address) values (?,?,?,?,?); SELECT MAX(id) FROM User;"
        try {
            db.update(
                SQL,
                user.username,
                passwordEncoder.encode(user.password),
                user.email,
                user.phone,
                user.address)

            var id = db.query("SELECT MAX(id) as ret FROM user", intRowMapper)
            println(id)
            user.id = id[0]
            return user

        } catch (e: Exception) {
            println(e.message)
            return null
        }
    }

    fun getOne(id: Int): UserDto? {
        val SQL = "SELECT * FROM USER WHERE id = ?"
        var args = Util.convertToObject(id)
        val res = db.query(
            SQL,
            BeanPropertyRowMapper(UserDto::class.java),
            id
        )
        if (res.size > 0) {
            return res[0]
        } else {
            return null
        }

    }

    fun updateOne(id: Int, user: UserDto): UserDto {
        val sql = "update User set username = ?, password = ?, email = ?, phone = ?, address = ? where id = ?"
        val num = db.update(sql, user.username, passwordEncoder.encode(user.password), user.email, user.phone, user.address, id)

        if (num == 1) {
            user.id = id
            return user
        } else {
            return UserDto()
        }
    }

    fun deleteOne(id: Int): UserDto? {
        val sql = "delete from user where id = ?"

        var user = getOne(id);

        var num = db.update(sql, id)

        if (num == 1) {
            return user
        } else {
            return null
        }
    }

    private val rowMapper = RowMapper { rs: ResultSet, rowNum: Int ->
        val result = UserDto(
            rs.getString("username"),
            rs.getString("password"),
            rs.getString("email"),
            rs.getString("phone"),
            rs.getString("address")
        )
        result.id = rs.getInt("id")
        result
    }

    private val intRowMapper = RowMapper { rs: ResultSet, rowNum: Int ->
        val res = rs.getInt("ret")

        res
    }
}

