package org.nicolai.bank.dto

import java.time.LocalDateTime


class TransactionDto() {

    var id: Int? = null
    var sender: Int? = null
    var receiver: Int? = null
    var date: LocalDateTime? = null
    var amount: Double? = null

    constructor(sender: Int, receiver: Int, date: LocalDateTime, amount: Double): this() {
        this.sender = sender
        this.receiver = receiver
        this.date = date
        this.amount = amount
    }

}
