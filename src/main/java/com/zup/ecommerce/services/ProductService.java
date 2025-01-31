package com.zup.ecommerce.services;

import java.util.List;

import com.zup.ecommerce.dtos.ProductRequestDTO;
import com.zup.ecommerce.models.Product;

public interface ProductService {
    Product createProduct(ProductRequestDTO productRequestDTO);
    Product findProductById(Long id);
    List<Product> findAllProducts();
    void deleteProductById(Long id);
}