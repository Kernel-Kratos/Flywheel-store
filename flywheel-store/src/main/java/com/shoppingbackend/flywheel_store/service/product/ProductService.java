package com.shoppingbackend.flywheel_store.service.product;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import com.shoppingbackend.flywheel_store.model.Category;
import com.shoppingbackend.flywheel_store.model.Image;
import com.shoppingbackend.flywheel_store.repository.ImageRepository;
import com.shoppingbackend.flywheel_store.model.Product;
import com.shoppingbackend.flywheel_store.repository.CategoryRepository;
import com.shoppingbackend.flywheel_store.repository.ProductRepository;
import com.shoppingbackend.flywheel_store.request.AddProductRequest;
import com.shoppingbackend.flywheel_store.request.UpdateProductRequest;

import lombok.RequiredArgsConstructor;

import com.shoppingbackend.flywheel_store.dto.ImageDto;
import com.shoppingbackend.flywheel_store.dto.ProductDto;
import com.shoppingbackend.flywheel_store.exceptions.CategoryNotFoundExcaption;
import com.shoppingbackend.flywheel_store.exceptions.ProductNotFoundException;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    @Override
    public Product addProduct(AddProductRequest request) {
        // Check : category found in database
        // If yes set is as new product cat else save it as new cat then set it as new product cat 
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                            .orElseGet(() -> {
                                Category newCategory = new Category(request.getCategory().getName());
                                return categoryRepository.save(newCategory);
                            });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }
    private Product createProduct(AddProductRequest request, Category category){
        return new Product(
            request.getName(),
            request.getBrand(),
            request.getPrice(),
            request.getInventory(),
            request.getDescription(),
            category
            );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                                    .orElseThrow(() -> new ProductNotFoundException("Product not Found!"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id).
            ifPresentOrElse(productRepository :: delete, 
                () -> {throw new ProductNotFoundException("Product not Found!!!");});
    }

    @Override
    public Product updateProduct(UpdateProductRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(exisitngProduct -> updateExisitingProduct(exisitngProduct, request))
                .map(productRepository :: save)
                .orElseThrow(() -> new ProductNotFoundException("ProductNotFound"));
    }

    private Product updateExisitingProduct(Product exisitingProduct, UpdateProductRequest request){
        exisitingProduct.setName(request.getName());
        exisitingProduct.setBrand(request.getBrand());
        exisitingProduct.setPrice(request.getPrice());
        exisitingProduct.setInventory(request.getInventory());
        exisitingProduct.setDescription(request.getDescription());

        Category exisitingCategory =  categoryRepository.findByName(request.getCategory().getName());
        //.getName() will return null if the category isn't available and this will potentially orphan the product as we will set its cat as null.
        Category category = Optional.ofNullable(exisitingCategory)
                            .orElseThrow(()-> new CategoryNotFoundExcaption("Category wans't found")); 
        exisitingProduct.setCategory(category); 
        return exisitingProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);   
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }
    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

    @Override
    public List<ProductDto> getConvertedProducts(List <Product> products){
        return products.stream().map(this::convertToProductDto).toList();
    }
    @Override
    public ProductDto convertToProductDto (Product product){
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos = images.stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .toList();
        productDto.setImages(imageDtos);
        return productDto;

    }
}
