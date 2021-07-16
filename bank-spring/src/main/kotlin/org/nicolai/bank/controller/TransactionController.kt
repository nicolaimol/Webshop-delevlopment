package org.nicolai.bank.controller

import org.nicolai.bank.dto.TransactionDto
import org.nicolai.bank.service.TransactionService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/transaction")
class TransactionController(val transactionService: TransactionService) {

    @PostMapping
    fun addTransaction(@RequestBody transactionDto: TransactionDto): TransactionDto {
        return transactionService.addOne(transactionDto)
    }

    @GetMapping
    fun getAll(): List<TransactionDto> {
        return transactionService.getAll()
    }

    @GetMapping("/{id}")
    fun getByUser(@PathVariable id: Int): Map<String, Any> {
        return transactionService.getByUser(id)
    }

}
