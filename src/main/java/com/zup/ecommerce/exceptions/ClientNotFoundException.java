package com.zup.ecommerce.exceptions;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(String message) {
        super(message); // Passa a mensagem para a classe RuntimeException
    }
}
