package com.zup.ecommerce.services;

import java.util.List;

import com.zup.ecommerce.utils.ValidationUtils;
import org.springframework.stereotype.Service;

import com.zup.ecommerce.models.Product;
import com.zup.ecommerce.repositories.ProductRepository;
import com.zup.ecommerce.exceptions.ProductNotFoundException;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product productToCreate) {
        ValidationUtils.validateNotEmpty(productToCreate.getName(), "Nome do Produto");
        ValidationUtils.validatePositive(productToCreate.getPrice(), "Preço do Produto");
        ValidationUtils.validateNonNegative(productToCreate.getAmount(), "Quantidade do Produto");
        if (productRepository.existsByNameIgnoreCase(productToCreate.getName())) {
            throw new IllegalArgumentException("Já existe um produto com este nome.");
        }
        return productRepository.save(productToCreate);
    }

    @Override
    public Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produto de id: " + id + "não encontrado."));
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

}
