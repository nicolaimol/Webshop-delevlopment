package org.nicolai.bank.dto

class UserDto() {

    var id: Int? = null
    var username: String? = null
    var password: String? = null
    var email: String? = null
    var phone: String? = null
    var address: String? = null


    constructor(username: String, password: String, email: String, phone: String, address: String): this() {
        this.username = username
        this.password = password
        this.email = email
        this.phone = phone
        this.address = address
    }

    override fun toString(): String {
        return "UserDto(id=$id, username=$username, password=$password, email=$email, phone=$phone, address=$address)"
    }


}
