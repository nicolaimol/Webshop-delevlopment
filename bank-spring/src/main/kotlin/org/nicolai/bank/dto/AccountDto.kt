package org.nicolai.bank.dto

class AccountDto() {

    var id: Int? = null
    var type: String? = null
    var balance: Double? = null
    var owner: Int? = null

    constructor(type: String, balance: Double, owner: Int): this() {
        this.type = type
        this.balance = balance
        this.owner = owner
    }

    override fun toString(): String {
        return "AccountDto(id=$id, type=$type, balance=$balance, owner=$owner)"
    }


}
