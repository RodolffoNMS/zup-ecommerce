package com.zup.ecommerce.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ClientRequestDTO {
    @NotBlank(message = "O nome não pode ser vazio.")
    private String name;

    @NotBlank(message = "O CPF não pode ser vazio.")
    @Pattern(regexp = "\\d{11}", message = "O CPF informado é inválido.")
    private String cpf;

    @NotBlank(message = "O email não pode ser vazio.")
    @Email(message = "O email informado é inválido.")
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}