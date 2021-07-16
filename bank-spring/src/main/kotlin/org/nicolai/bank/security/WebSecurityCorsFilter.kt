package org.nicolai.bank.security

import java.io.IOException
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import kotlin.Throws
import javax.servlet.http.HttpServletResponse

class WebSecurityCorsFilter : Filter {
    @Throws(ServletException::class)
    override fun init(filterConfig: FilterConfig) {
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {

        //println("cors filter")

        val res = response as HttpServletResponse
        res.setHeader("Access-Control-Allow-Origin", "http://localhost:5000")
        res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT")
        res.setHeader("Access-Control-Max-Age", "3600")
        res.setHeader("Access-Control-Allow-Credentials", "true")
        res.setHeader(
            "Access-Control-Allow-Headers",
            "Auth, Content-Type, Accept, x-requested-with, Cache-Control"
        )

        val req = request as HttpServletRequest
        if (req.method == "OPTIONS") {
            return
        }

        chain.doFilter(req, res)
    }

    override fun destroy() {}
}
