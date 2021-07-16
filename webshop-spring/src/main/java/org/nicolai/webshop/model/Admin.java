package org.nicolai.webshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.nicolai.webshop.security.ApplicationUserRole;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Admin {

    @Id
    private String username;
    private String password;
    private ApplicationUserRole role;
}
