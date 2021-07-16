package org.nicolai.webshop.service;

import org.nicolai.webshop.model.Product;
import org.nicolai.webshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public List<Product> getAll() {
        return repository.findAll();
    }

    public Product getById (int id) {
        return repository.findById(id).orElse(null);
    }

    public Product addOne(Product product) {
        return repository.save(product);
    }

    public Product update(int id, Product product) {
        product.setId(id);
        return repository.save(product);
    }

    public Product delete (int id) {
        var res = repository.findById(id);
        if (res.isPresent()) {
            repository.deleteById(id);
            return res.get();
        } else {
            return null;
        }
    }
}
