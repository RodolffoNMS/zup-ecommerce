package com.zup.ecommerce.dtos;


import java.util.List;

public class PurchaseRequestDTO {
    private String cpf;
    private List<String> produtos;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<String> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<String> produtos) {
        this.produtos = produtos;
    }
}
