package com.template.exception;

/**
 * Exceção personalizada para representar erros de validação de dados de entrada e regras de negócio.
 */
public class ValidationException extends RuntimeException {
    
    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
