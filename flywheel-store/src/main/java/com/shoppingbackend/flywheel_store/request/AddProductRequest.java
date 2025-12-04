/*Why we creating request package? 
 * 
 * we are creating this package because we it is not advisable to directly work witht he model classes.
 * it is because it holds many things like relations etc and we might run into problems due to relations
 * eg: when adding a product we might not add images at that time so it will create problems when adding only product/s 
*/



package com.shoppingbackend.flywheel_store.request;

import java.math.BigDecimal;

import com.shoppingbackend.flywheel_store.model.Category;

import lombok.Data;

//@Data generates many things like hashcode, toString etc
// It is not recommemend to use @Data direclty on a entity so that's why we are using it here since it not an entity
@Data
public class AddProductRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
