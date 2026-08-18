package com.template.service;

import com.template.exception.ValidationException;
import com.template.validator.UsuarioValidator;

/**
 * Classe de Serviço para Usuários, demonstrando a aplicação do princípio SRP (Single Responsibility Principle)
 * e servindo como referência de arquitetura em camadas para a aplicação.
 */
public class UsuarioService {

    private final UsuarioValidator usuarioValidator;

    public UsuarioService() {
        this(new UsuarioValidator());
    }

    public UsuarioService(UsuarioValidator usuarioValidator) {
        this.usuarioValidator = usuarioValidator;
    }

    /**
     * Valida os dados de cadastro de usuário e executa as regras de negócio associadas.
     *
     * @param nome  Nome do usuário
     * @param email E-mail do usuário
     * @param senha Senha do usuário
     * @throws ValidationException se os dados forem inválidos
     */
    public void cadastrarUsuario(String nome, String email, String senha) throws ValidationException {
        usuarioValidator.validarCampos(nome, email, senha);
        usuarioValidator.validarEmail(email);
        // Lógica de negócio, hashing de senha e chamada ao DAO de persistência
    }
}
