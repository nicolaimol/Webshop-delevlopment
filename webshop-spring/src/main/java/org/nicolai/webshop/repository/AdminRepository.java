package org.nicolai.webshop.repository;

import org.nicolai.webshop.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, String> {
    Admin findByUsername(String username);
}
