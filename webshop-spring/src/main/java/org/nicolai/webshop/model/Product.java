package org.nicolai.webshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {

    @Id
    @GeneratedValue
    private int id;
    private String item_number;
    private String name;
    private String description;
    private int quantity;
    private double price;
    private String url;

    @OneToMany(mappedBy = "product")
    List<OrderLine> orderLines;
}
