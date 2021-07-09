package org.nicolai.bank.service

import org.nicolai.bank.dto.UserDto
import org.nicolai.bank.repository.UserRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository) {

    fun getOne(id: Int): UserDto? {
        return userRepository.getOne(id)
    }

    fun getAll(): List<UserDto> {
        return userRepository.getAll()
    }

    fun addUser (user: UserDto): UserDto? {

        return userRepository.addUser(user)

    }

    fun updateOne(id: Int, user: UserDto): UserDto {
        return userRepository.updateOne(id, user)
    }

    fun deleteOne(id: Int): UserDto? {
        return userRepository.deleteOne(id)
    }


}
