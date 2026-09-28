package com.template.validator;

import com.template.util.DialogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementação da interface IUsuarioValidator utilizando lista de validadores especializados (conforme slide 18 do PDF).
 */
public class UsuarioValidator implements IUsuarioValidator {

    /**
     * Valida todos os campos de usuário iterando sobre a lista de validadores.
     *
     * @param nome  Nome do usuário
     * @param email E-mail do usuário
     * @param senha Senha do usuário
     * @param login Login do usuário
     * @return true se todos os campos forem válidos; false caso algum falhe
     */
    @Override
    public boolean validarUsuario(String nome, String email, String senha, String login) {
        // Lista de validadores que serão aplicados sequencialmente
        List<Validador<String>> validadores = new ArrayList<>();

        // Adicionando os validadores de campos obrigatórios
        validadores.add(new CampoObrigatorioValidador("Nome", nome));
        validadores.add(new CampoObrigatorioValidador("E-mail", email));
        validadores.add(new CampoObrigatorioValidador("Senha", senha));
        validadores.add(new CampoObrigatorioValidador("Login", login));

        // Adicionando o validador específico de formato de e-mail
        validadores.add(new EmailValidador(email));

        // Itera sobre a lista de validadores
        for (Validador<String> validador : validadores) {
            if (!validador.validar(validador.getValor())) {
                DialogUtil.showWarning(validador.getMensagemErro());
                return false; // Retorna falso na primeira falha de validação
            }
        }

        return true; // Todos os validadores passaram
    }
}
