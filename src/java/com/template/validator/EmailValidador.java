package com.template.validator;

import java.util.regex.Pattern;

/**
 * Validador específico para o formato de e-mail utilizando expressões regulares (conforme slide 17 do PDF).
 */
public class EmailValidador implements Validador<String> {

    private static final String EMAIL_REGEX = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern PATTERN = Pattern.compile(EMAIL_REGEX);
    private final String email;

    public EmailValidador(String email) {
        this.email = email;
    }

    @Override
    public boolean validar(String valorAtual) {
        return this.email != null && PATTERN.matcher(this.email.trim()).matches();
    }

    @Override
    public String getMensagemErro() {
        return "Digite um e-mail válido (exemplo@dominio.com)!";
    }

    @Override
    public String getValor() {
        return email;
    }
}
