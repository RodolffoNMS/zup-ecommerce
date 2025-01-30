package com.zup.ecommerce.services;

import java.util.List;

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
        if (productToCreate.getName() == null || productToCreate.getName().isEmpty()) {
            throw new IllegalArgumentException("O nome do proguto não pode ser vazio.");
        }
        if (productToCreate.getPrice() == null || productToCreate.getPrice() <= 0) {
            throw new IllegalArgumentException("O valor do proguto deve ser maior que zero.");
        }
        if (productToCreate.getAmount() < 0) {
            throw new IllegalArgumentException("A quantidade desse produto não pode ser negativa.");
        }
        /*
         * Esse código verifica se já existe um produto com o mesmo nome no repositório antes de criar um novo produto:
          
         * productRepository.findAll():Obtém uma lista de todos os produtos armazenados no repositório (banco de dados ou outra fonte de dados).
          
         * stream():Converte a lista de produtos em um fluxo (stream), permitindo processar os elementos de forma funcional.
         
         * anyMatch(product -> product.getName().equalsIgnoreCase(productToCreate.getName())):
         * Verifica se algum produto na lista tem o mesmo nome (ignorando maiúsculas e minúsculas) que o produto que está sendo criado (productToCreate).
         * A função equalsIgnoreCase compara strings sem diferenciar maiúsculas de minúsculas.
                  
         * throw new IllegalArgumentException:
         * Lança uma exceção (IllegalArgumentException) com a mensagem "Já existe um produto com este nome.".
        */
        if (productRepository.findAll().stream()
                .anyMatch(product -> product.getName().equalsIgnoreCase(productToCreate.getName()))) {
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
