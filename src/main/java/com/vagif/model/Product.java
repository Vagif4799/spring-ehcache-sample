package com.vagif.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="product_id")
    private int id;
    @Column(name="product_name")
    private String name;
    @Column(name="product_price")
    private BigDecimal price;

}
