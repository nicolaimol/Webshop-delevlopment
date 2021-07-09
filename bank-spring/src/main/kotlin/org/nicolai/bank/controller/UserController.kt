package org.nicolai.bank.controller

import org.nicolai.bank.dto.UserDto
import org.nicolai.bank.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/user")
class UserController(val userService: UserService) {


    @GetMapping
    fun getUsers(): List<UserDto> {
        return userService.getAll()
    }

    @PostMapping
    fun addUser(@RequestBody userDto: UserDto): UserDto {
        val res: UserDto? = userService.addUser(userDto)
        if (res != null) {
            return res
        } else {
            return UserDto()
        }

    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Int): UserDto {

        val user = userService.getOne(id)

        if (user != null) {
            return user
        } else {
            return UserDto()
        }
    }

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Int, @RequestBody userDto: UserDto): UserDto {
        return userService.updateOne(id, userDto)
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Int): UserDto {
        var user =  userService.deleteOne(id)
        return user ?: UserDto()
    }
}
