package com.zup.ecommerce.utils;


import java.math.BigDecimal;

public final class ValidationUtils {

    private ValidationUtils() {
        throw new UnsupportedOperationException("Esta é uma classe utilitária e não pode ser instanciada.");
    }

    public static void validateNotEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " não pode ser vazio.");
        }
    }
    public static void validateCpf(String cpf) {
        if (!CPFUtils.isValidCPF(cpf)) {
            throw new IllegalArgumentException("O CPF informado é inválido.");
        }
    }
    public static void validateEmail(String email) {
        if (email == null || !email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("O email informado é inválido.");
        }
    }

    public static void validatePositive(BigDecimal value, String fieldName) {
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(fieldName + " deve ser maior que zero.");
        }
    }

    public static void validateNonNegative(Integer value, String fieldName) {
        if (value == null || value < 0) {
            throw new IllegalArgumentException(fieldName + " não pode ser negativo.");
        }
    }
}
