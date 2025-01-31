package com.zup.ecommerce.services.impl;

import com.zup.ecommerce.dtos.PurchaseRequestDTO;
import com.zup.ecommerce.models.Client;
import com.zup.ecommerce.models.Compra;
import com.zup.ecommerce.models.Product;
import com.zup.ecommerce.repositories.ClientRepository;
import com.zup.ecommerce.repositories.CompraRepository;
import com.zup.ecommerce.repositories.ProductRepository;
import com.zup.ecommerce.services.CompraService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompraServiceImpl implements CompraService {
    private final CompraRepository compraRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;


    public CompraServiceImpl(CompraRepository compraRepository, ProductRepository productRepository, ClientRepository clientRepository) {
        this.compraRepository = compraRepository;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;

    }

    @Override
    public Compra createCompra(PurchaseRequestDTO purchaseRequest) {
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

        // Criar compra
        Compra compra = new Compra();
        compra.setClient(client);
        compra.setProdutos(produtos);
        compra.setTotal(total);

        return compraRepository.save(compra);
    }

    @Override
    public Compra findCompraById(Long id) {
        return compraRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Compra com ID " + id + " não encontrada."));
    }

    @Override
    public List<Compra> findAllCompras() {
        return compraRepository.findAll();
    }
}

