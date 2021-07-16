package org.nicolai.webshop.service;

import org.nicolai.webshop.dto.OrderRequestDto;
import org.nicolai.webshop.model.Orders;
import org.nicolai.webshop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
public class OrderService {

    @Autowired

    private OrderRepository repository;

    public List<Orders> findAll() {
        return repository.findAll();
    }

    public Orders findById (int id) {
        return repository.findById(id).orElse(null);
    }

    public Orders addOne(Orders order) {
        return repository.save(order);
    }

    public Orders update (int id, Orders order) {
        order.setId(id);
        return repository.save(order);
    }

    public Orders delete(int id) {
        var res = repository.findById(id);
        if (res.isPresent()) {
           repository.deleteById(id);
           return res.get();
        }  else {
           return null;
        }
    }

}
