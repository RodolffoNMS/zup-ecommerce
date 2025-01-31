package com.zup.ecommerce.services;

import com.zup.ecommerce.dtos.PurchaseRequestDTO;
import com.zup.ecommerce.models.Compra;

import java.util.List;

public interface CompraService {
    Compra createCompra(PurchaseRequestDTO purchaseRequest);
    Compra findCompraById(Long id);
    List<Compra> findAllCompras();
}