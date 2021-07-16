package org.nicolai.bank.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/api/test")
class TestController {

    @PostMapping
    fun test(@RequestBody string: String, httpServletResponse: HttpServletResponse): ResponseEntity<Any> {
        //println(string + " error " + (string == "error"))

        if (string == "error") {
            //println("error")

            return ResponseEntity(hashMapOf("msg" to "error"), HttpStatus.BAD_REQUEST)
        } else {
            var map = HashMap<String, String>()
            map["msg"] = "hey"

            return ResponseEntity(map, HttpStatus.OK)
        }

    }

}
