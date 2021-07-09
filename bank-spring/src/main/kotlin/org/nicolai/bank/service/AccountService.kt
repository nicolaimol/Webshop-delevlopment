package org.nicolai.bank.service

import org.nicolai.bank.dto.AccountDto
import org.nicolai.bank.repository.AccountRepository
import org.springframework.stereotype.Service

@Service
class AccountService(val accountRepository: AccountRepository) {

    fun getAll(): List<AccountDto> {
        return accountRepository.getAll()
    }

    fun addOne(accountDto: AccountDto): AccountDto {
        return accountRepository.addOne(accountDto)
    }

    fun getByUser(id: Int): List<AccountDto> {
        return accountRepository.getByUser(id)
    }

    fun getOne(id: Int): AccountDto {
        return accountRepository.getOne(id)
    }

    fun updateOne(id: Int, accountDto: AccountDto): AccountDto {
        return accountRepository.updateOne(id, accountDto)
    }

    fun deleteOne(id: Int): AccountDto {
        return accountRepository.deleteOne(id)
    }

}
