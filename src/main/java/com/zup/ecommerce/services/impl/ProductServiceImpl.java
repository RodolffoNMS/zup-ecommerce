package com.zup.ecommerce.services.impl;

import java.util.List;

import com.zup.ecommerce.dtos.ProductRequestDTO;
import com.zup.ecommerce.services.ProductService;
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
    public Product createProduct(ProductRequestDTO productToCreate) {
        ValidationUtils.validateNotEmpty(productToCreate.getName(), "Nome do Produto");
        ValidationUtils.validatePositive(productToCreate.getPrice(), "Preço do Produto");
        ValidationUtils.validateNonNegative(productToCreate.getAmount(), "Quantidade do Produto");

        if (productRepository.existsByNameIgnoreCase(productToCreate.getName())) {
            throw new IllegalArgumentException("Já existe um produto com este nome.");
        }

        // Converter ProductRequestDTO para Product
        Product product = new Product();
        product.setName(productToCreate.getName());
        product.setPrice(productToCreate.getPrice());
        product.setAmount(productToCreate.getAmount());

        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produto de id: " + id + " não encontrado."));
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void deleteProductById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Produto de id: " + id + " não encontrado.");
        }
        productRepository.deleteById(id);
    }
}
