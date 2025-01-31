package com.zup.ecommerce.controllers;


import com.zup.ecommerce.dtos.PurchaseRequestDTO;
import com.zup.ecommerce.dtos.PurchaseResponseDTO;
import com.zup.ecommerce.models.Compra;
import com.zup.ecommerce.models.Product;
import com.zup.ecommerce.services.CompraService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/compra")
public class CompraController {
    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @PostMapping
    public ResponseEntity<PurchaseResponseDTO> createCompra(@RequestBody PurchaseRequestDTO purchaseRequest) {
        Compra createdCompra = compraService.createCompra(purchaseRequest);

        // Converter Compra para PurchaseResponseDTO
        PurchaseResponseDTO responseDTO = new PurchaseResponseDTO();
        responseDTO.setId(createdCompra.getId());
        responseDTO.setClientName(createdCompra.getClient().getName());
        responseDTO.setProdutos(createdCompra.getProdutos().stream().map(Product::getName).toList());
        responseDTO.setTotal(createdCompra.getTotal());

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseResponseDTO> getCompraById(@PathVariable Long id) {
        Compra compra = compraService.findCompraById(id);

        // Converter Compra para PurchaseResponseDTO
        PurchaseResponseDTO responseDTO = new PurchaseResponseDTO();
        responseDTO.setId(compra.getId());
        responseDTO.setClientName(compra.getClient().getName());
        responseDTO.setProdutos(compra.getProdutos().stream().map(Product::getName).toList());
        responseDTO.setTotal(compra.getTotal());

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PurchaseResponseDTO>> getAllCompras() {
        List<Compra> compras = compraService.findAllCompras();

        // Converter a lista de Compras para uma lista de PurchaseResponseDTO
        List<PurchaseResponseDTO> responseDTOs = compras.stream().map(compra -> {
            PurchaseResponseDTO responseDTO = new PurchaseResponseDTO();
            responseDTO.setId(compra.getId());
            responseDTO.setClientName(compra.getClient().getName());
            responseDTO.setProdutos(compra.getProdutos().stream().map(Product::getName).toList());
            responseDTO.setTotal(compra.getTotal());
            return responseDTO;
        }).toList();

        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

}
