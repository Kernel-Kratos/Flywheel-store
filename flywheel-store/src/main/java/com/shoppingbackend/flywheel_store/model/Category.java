package com.shoppingbackend.flywheel_store.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Product> products; // makes it one(Cat) to many(Prods) relation. Since category has many products. We can easily retrive all the products from a category.
     
    // HAd to generte this due to @AllArgs... generating Category(Long id, String name) and not Category(String name) which is required in product service layer
    public Category(String name){
        this.name = name;
    }

}
