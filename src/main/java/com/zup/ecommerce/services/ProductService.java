package com.zup.ecommerce.services;

import java.util.List;

import com.zup.ecommerce.models.Product;

public interface ProductService {

    Product createProduct(Product productToCreated);
    Product findProductById(Long id);
    List<Product> findAllProducts();
}
