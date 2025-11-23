package com.shoppingbackend.flywheel_store.model;

import java.sql.Blob;

import com.fasterxml.jackson.annotation.JsonBackReference;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filename;
    private String filetype;

    @Lob
    private Blob image; // BLOB = binary large object. Its type used for images, videos etc
    private String downloadUrl;
    
    @ManyToOne

    @JoinColumn(name = "product_id") //JoinColoum tells spring jpa that image will have a product_table which will have products primary key. 
    //Following needed to be added cuz there was recursion on 1 product.
    // JSON goes to product sees the private image.. and comes to image. See private Product goes to product and goes to product again. THis loop continues 
    @JsonBackReference//Tells Json I'm the child don't serialize me.
    private Product product;
}
