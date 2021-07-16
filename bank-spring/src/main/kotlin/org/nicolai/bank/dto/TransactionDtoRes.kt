package org.nicolai.bank.dto

import java.time.LocalDateTime


class TransactionDtoRes() {

    var trans: Int? = null
    var sender: Int? = null
    var receiver: Int? = null
    var senderU: String? = null
    var receiverU: String? = null
    var date: LocalDateTime? = null
    var amount: Double? = null

    constructor(sender: Int, receiver: Int, senderU: String, receiverU: String, date: LocalDateTime, amount: Double): this() {
        this.sender = sender
        this.receiver = receiver
        this.senderU = senderU
        this.receiverU = receiverU
        this.date = date
        this.amount = amount
    }

}
