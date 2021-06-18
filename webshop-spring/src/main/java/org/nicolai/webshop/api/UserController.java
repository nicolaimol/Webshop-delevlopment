package org.nicolai.webshop.api;

import org.nicolai.webshop.model.User;
import org.nicolai.webshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
//@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        boolean saved = userService.registerUser(user);
        if (saved) {
            return new ResponseEntity<>("User created", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("User not created", HttpStatus.BAD_REQUEST);
        }
    }
}
