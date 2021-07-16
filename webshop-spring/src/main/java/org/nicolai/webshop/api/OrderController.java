package org.nicolai.webshop.api;

import org.nicolai.webshop.model.Orders;
import org.nicolai.webshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Orders> getOrder() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public Orders getOrders(@PathVariable("id") int id) {
        return orderService.findById(id);
    }

    @PostMapping
    public Orders addOrder (@RequestBody Orders order) {
        return orderService.addOne(order);
    }

    @PutMapping("/{id}")
    public Orders update(@PathVariable("id") int id, @RequestBody Orders order) {
        return orderService.update(id, order);
    }

    @DeleteMapping("/{id}")
    public Orders delete(@PathVariable("id") int id) {
        return orderService.delete(id);
    }

}
