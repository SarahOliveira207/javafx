package com.template.validator;

public interface ICarroValidador {

    boolean validarCarro(String nome, String cavalos, String cor);

    /**
     * Retorna a mensagem da última validação que falhou, para ser exibida na tela.
     */
    String getMensagemErro();
}
