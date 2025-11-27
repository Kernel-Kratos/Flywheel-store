package com.shoppingbackend.flywheel_store.model;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    
    
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "category_id")
    private Category category;// maintains the bidirectional relation. When a product is loaded it category can be retrived easily.


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    //Following needed to be added cuz there was recursion on 1 product.
    // JSON goes to product sees the private image.. and comes to image. See private Product goes to product and goes to product again. THis loop continues
    @JsonManagedReference // Tells Json I mangage the relationship. Serialize me.
    private List<Image> images;

    public Product(String name, String brand, BigDecimal price, int inventory, String description,Category category) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.inventory = inventory;
        this.description = description;
        this.category = category;
    }
}
