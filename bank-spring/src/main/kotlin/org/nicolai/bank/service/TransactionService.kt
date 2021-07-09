package org.nicolai.bank.service

import org.nicolai.bank.dto.TransactionDto
import org.nicolai.bank.repository.SqlException
import org.nicolai.bank.repository.TransactionRepository
import org.springframework.stereotype.Service


@Service
class TransactionService(val transactionRepository: TransactionRepository) {

    fun getAll(): List<TransactionDto> {
        return transactionRepository.getAll()
    }

    fun addOne(transactionDto: TransactionDto): TransactionDto {
        try {
            return transactionRepository.addOne(transactionDto)
        } catch (e: SqlException) {
            return TransactionDto()
        }

    }

}
