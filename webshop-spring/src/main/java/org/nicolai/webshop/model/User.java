package org.nicolai.webshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;
    @Column(unique = true)
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;

    @OneToMany(mappedBy = "user")
    List<Orders> orders;
}
