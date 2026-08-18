package com.template.validator;

import com.template.exception.ValidationException;

import java.util.regex.Pattern;

/**
 * Classe responsável pela validação de dados de usuário (campos obrigatórios, formato de e-mail, etc.) seguindo o SRP.
 */
public class UsuarioValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    /**
     * Valida campos genéricos obrigatórios de usuário.
     *
     * @param campos Valores a serem verificados
     * @throws ValidationException se algum campo for nulo ou vazio
     */
    public void validarCampos(String... campos) throws ValidationException {
        if (campos == null || campos.length == 0) {
            throw new ValidationException("Os campos obrigatórios não foram preenchidos.");
        }
        for (String campo : campos) {
            if (campo == null || campo.trim().isEmpty()) {
                throw new ValidationException("Todos os campos obrigatórios devem ser preenchidos.");
            }
        }
    }

    /**
     * Valida o formato de um e-mail utilizando expressão regular.
     *
     * @param email Endereço de e-mail
     * @return true se o e-mail for válido
     * @throws ValidationException se o e-mail estiver em formato inválido
     */
    public boolean validarEmail(String email) throws ValidationException {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("O campo 'E-mail' é obrigatório.");
        }
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new ValidationException("O e-mail informado '" + email + "' possui formato inválido.");
        }
        return true;
    }
}
