package org.nicolai.webshop.api;

import org.nicolai.webshop.model.Product;
import org.nicolai.webshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    ProductService service;

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Product product) {
        var res = service.addOne(product);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        var res = service.getAll();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById (@PathVariable("id") int id) {
        var res = service.getById(id);
        if (res != null) return ResponseEntity.ok(res);
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update (@PathVariable("id") int id, @RequestBody Product product) {
        var res = service.update(id, product);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete (@PathVariable("id") int id) {
        var res = service.delete(id);
        if (res != null) {
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.notFound().build();
    }

}
