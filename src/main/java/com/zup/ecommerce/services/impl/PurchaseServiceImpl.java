package com.zup.ecommerce.services.impl;

import com.zup.ecommerce.dtos.PurchaseRequestDTO;
import com.zup.ecommerce.models.Client;
import com.zup.ecommerce.models.Purchase;
import com.zup.ecommerce.models.Product;
import com.zup.ecommerce.repositories.ClientRepository;
import com.zup.ecommerce.repositories.PurchaseRepository;
import com.zup.ecommerce.repositories.ProductRepository;
import com.zup.ecommerce.services.PurchaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;


    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, ProductRepository productRepository, ClientRepository clientRepository) {
        this.purchaseRepository = purchaseRepository;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;

    }

    @Override
    public Purchase createPurchase(PurchaseRequestDTO purchaseRequest) {
        // Buscar cliente pelo CPF
        Client client = clientRepository.findByCpf(purchaseRequest.getCpf())
                .orElseThrow(() -> new IllegalArgumentException("Cliente com CPF " + purchaseRequest.getCpf() + " não encontrado."));

        // Buscar produtos pelo nome e validar estoque
        List<Product> produtos = purchaseRequest.getProdutos().stream()
                .map(productName -> {
                    Product product = productRepository.findByNameIgnoreCase(productName)
                            .orElseThrow(() -> new IllegalArgumentException("Produto " + productName + " não encontrado."));

                    // Verificar estoque
                    if (product.getAmount() <= 0) {
                        throw new IllegalArgumentException("Produto " + product.getName() + " está fora de estoque.");
                    }

                    // Atualizar estoque
                    product.setAmount(product.getAmount() - 1);
                    productRepository.save(product);

                    return product;
                })
                .toList();

        // Calcular total
        double total = produtos.stream()
                .mapToDouble(product -> product.getPrice().doubleValue())
                .sum();

        // Criar purchase
        Purchase purchase = new Purchase();
        purchase.setClient(client);
        purchase.setProdutos(produtos);
        purchase.setTotal(total);

        return purchaseRepository.save(purchase);
    }

    @Override
    public Purchase findPurchaseById(Long id) {
        return purchaseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Purchase com ID " + id + " não encontrada."));
    }

    @Override
    public List<Purchase> findAllPurchases() {
        return purchaseRepository.findAll();
    }
}

