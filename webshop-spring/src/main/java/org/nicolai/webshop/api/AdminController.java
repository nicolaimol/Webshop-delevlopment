package org.nicolai.webshop.api;

import org.nicolai.webshop.model.Admin;
import org.nicolai.webshop.model.User;
import org.nicolai.webshop.service.AdminService;
import org.nicolai.webshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping
    public ResponseEntity<Object> register(@RequestBody Admin user) {
        Admin saved = adminService.registerUser(user);
        if (saved != null) {
            return new ResponseEntity<>("User created", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("User not created", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return new ResponseEntity<>(adminService.getAll(),HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<Object> getById(@RequestParam String username) {
        var user = adminService.getById(username);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestParam("id") String id, @RequestBody Admin user) {
        var res = adminService.update(id, user);
        if (res != null) return new ResponseEntity<>(res, HttpStatus.OK);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestParam String username) {
        var res = adminService.delete(username);
        if (res != null) return ResponseEntity.ok(res);
        return ResponseEntity.notFound().build();
    }
}
