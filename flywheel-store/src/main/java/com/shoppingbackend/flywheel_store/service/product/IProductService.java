package com.shoppingbackend.flywheel_store.service.product;

import java.util.List;

import com.shoppingbackend.flywheel_store.dto.ProductDto;
import com.shoppingbackend.flywheel_store.model.Product;
import com.shoppingbackend.flywheel_store.request.AddProductRequest;
import com.shoppingbackend.flywheel_store.request.UpdateProductRequest;

public interface IProductService {
    Product addProduct (AddProductRequest request);
    Product getProductById (Long id);
    void deleteProductById (Long id);
    Product updateProduct (UpdateProductRequest request, Long productId);
    List<Product> getAllProducts ();
    List<Product> getProductsByCategory (String category);
    List<Product> getProductsByBrand (String brand);
    List<Product> getProductsByCategoryAndBrand (String category, String brand);
    List<Product> getProductsByName (String name);
    List<Product> getProductsByBrandAndName (String brand, String name);
    Long countProductByBrandAndName(String brand, String name);
    ProductDto convertToProductDto (Product product);
    List<ProductDto> getConvertedProducts(List<Product> products);
}
