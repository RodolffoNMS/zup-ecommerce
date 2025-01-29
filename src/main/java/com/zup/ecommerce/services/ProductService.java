package com.zup.ecommerce.services;

import com.zup.ecommerce.models.Product;

public interface ProductService {

    Product createProduct(Product productToCreated);
    Product finProductById(Long id);
    Product findAllProduct();
}
