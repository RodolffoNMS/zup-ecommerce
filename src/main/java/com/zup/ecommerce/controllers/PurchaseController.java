package com.zup.ecommerce.controllers;


import com.zup.ecommerce.dtos.PurchaseRequestDTO;
import com.zup.ecommerce.dtos.PurchaseResponseDTO;
import com.zup.ecommerce.models.Purchase;
import com.zup.ecommerce.models.Product;
import com.zup.ecommerce.services.PurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/purchase")
public class PurchaseController {
    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public ResponseEntity<PurchaseResponseDTO> createPurchase(@RequestBody PurchaseRequestDTO purchaseRequest) {
        Purchase createdPurchase = purchaseService.createPurchase(purchaseRequest);

        PurchaseResponseDTO responseDTO = new PurchaseResponseDTO();
        responseDTO.setId(createdPurchase.getId());
        responseDTO.setClientName(createdPurchase.getClient().getName());
        responseDTO.setProdutos(createdPurchase.getProdutos().stream().map(Product::getName).toList());
        responseDTO.setTotal(createdPurchase.getTotal());

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseResponseDTO> getPurchaseById(@PathVariable Long id) {
        Purchase purchase = purchaseService.findPurchaseById(id);

        PurchaseResponseDTO responseDTO = new PurchaseResponseDTO();
        responseDTO.setId(purchase.getId());
        responseDTO.setClientName(purchase.getClient().getName());
        responseDTO.setProdutos(purchase.getProdutos().stream().map(Product::getName).toList());
        responseDTO.setTotal(purchase.getTotal());

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PurchaseResponseDTO>> getAllPurchase() {
        List<Purchase> purchases = purchaseService.findAllPurchases();

        List<PurchaseResponseDTO> responseDTOs = purchases.stream().map(purchase -> {
            PurchaseResponseDTO responseDTO = new PurchaseResponseDTO();
            responseDTO.setId(purchase.getId());
            responseDTO.setClientName(purchase.getClient().getName());
            responseDTO.setProdutos(purchase.getProdutos().stream().map(Product::getName).toList());
            responseDTO.setTotal(purchase.getTotal());
            return responseDTO;
        }).toList();

        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

}
