package org.nicolai.bank.controller

import org.nicolai.bank.dto.AccountDto
import org.nicolai.bank.service.AccountService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/account")
class AccountController(val accountService: AccountService) {

    @GetMapping
    fun getAll(): List<AccountDto> {

        return accountService.getAll()
    }

    @PostMapping
    fun addAccount(@RequestBody accountDto: AccountDto): AccountDto {
        return accountService.addOne(accountDto)
    }

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Int): AccountDto {
        return accountService.getOne(id)
    }

    @GetMapping("/user/{id}")
    fun getByUser(@PathVariable id: Int): List<AccountDto> {
        return accountService.getByUser(id)
    }

    @PutMapping("/{id}")
    fun updateOne(@PathVariable id: Int, @RequestBody accountDto: AccountDto): AccountDto {
        return accountService.updateOne(id, accountDto)
    }

    @DeleteMapping("/{id}")
    fun deleteOne(@PathVariable id: Int): AccountDto {
        return accountService.deleteOne(id)
    }

}
