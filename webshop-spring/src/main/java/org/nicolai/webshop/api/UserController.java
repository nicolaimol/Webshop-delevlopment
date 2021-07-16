package org.nicolai.webshop.api;

import org.nicolai.webshop.model.User;
import org.nicolai.webshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
//@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody User user) {
        User saved = userService.registerUser(user);
        if (saved != null) {
            return new ResponseEntity<>("User created", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("User not created", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return new ResponseEntity<>(userService.getAll(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") int id) {
        var user = userService.getById(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") int id, @RequestBody User user) {
        var res = userService.update(id, user);
        if (res != null) return new ResponseEntity<>(res, HttpStatus.OK);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        var res = userService.delete(id);
        if (res != null) return ResponseEntity.ok(res);
        return ResponseEntity.notFound().build();
    }
}
