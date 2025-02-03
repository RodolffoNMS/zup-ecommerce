package com.zup.ecommerce.services;

import com.zup.ecommerce.dtos.PurchaseRequestDTO;
import com.zup.ecommerce.models.Purchase;

import java.util.List;

public interface PurchaseService {
    Purchase createPurchase(PurchaseRequestDTO purchaseRequest);
    Purchase findPurchaseById(Long id);
    List<Purchase> findAllPurchases();
}