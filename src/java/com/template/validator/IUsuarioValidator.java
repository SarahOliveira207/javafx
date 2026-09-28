package com.template.validator;

/**
 * Interface que define o contrato de validação de dados de usuário (conforme imagem de referência do projeto).
 */
public interface IUsuarioValidator {

    /**
     * Valida os dados de um usuário para cadastro ou atualização.
     *
     * @param nome  Nome do usuário
     * @param email E-mail do usuário
     * @param senha Senha do usuário
     * @param login Login do usuário
     * @return true se todos os campos forem válidos; false caso contrário
     */
    boolean validarUsuario(String nome, String email, String senha, String login);
}
