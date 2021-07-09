package org.nicolai.bank.repository

import java.lang.RuntimeException

class SqlException(msg: String?) : RuntimeException(msg)
