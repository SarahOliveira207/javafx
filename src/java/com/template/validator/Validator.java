package com.template.validator;

public class Validator<T> {
    boolean validar(T valor);
    String getMensagemErro();
    T getValor();
}
