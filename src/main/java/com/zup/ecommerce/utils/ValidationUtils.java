package com.zup.ecommerce.utils;

//Será final para impedir herança
public final class ValidationUtils {

    // O Construtor privado evita instanciação
    private ValidationUtils() {
        throw new UnsupportedOperationException("Esta é uma classe utilitária e não pode ser instanciada.");
    }

    // Validação de campos não vazios
    public static void validateNotEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " não pode ser vazio.");
        }
    }

    // Validação de CPF
    public static void validateCpf(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("O CPF informado é inválido.");
        }
    }

    // Validação de email
    public static void validateEmail(String email) {
        if (email == null || !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("O email informado é inválido.");
        }
    }

    // Validação de valores positivos
    public static void validatePositive(Double value, String fieldName) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException(fieldName + " deve ser maior que zero.");
        }
    }

    // Validação de valores inteiros não negativos
    public static void validateNonNegative(Integer value, String fieldName) {
        if (value == null || value < 0) {
            throw new IllegalArgumentException(fieldName + " não pode ser negativo.");
        }
    }
}
