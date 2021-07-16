package org.nicolai.bank.dto

class AccountDto() {

    var id: Int? = null
    var type: String? = null
    var name: String? = null
    var balance: Double? = null
    var owner: Int? = null

    constructor(type: String, name: String, balance: Double, owner: Int): this() {
        this.type = type
        this.name = name
        this.balance = balance
        this.owner = owner
    }

    override fun toString(): String {
        return "AccountDto(id=$id, type=$type, balance=$balance, owner=$owner)"
    }


}
