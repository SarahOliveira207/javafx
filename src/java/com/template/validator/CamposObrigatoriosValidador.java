package com.template.validator;

/**
 * Validador para garantir o preenchimento obrigatório de campos de texto.
 * Atende ao Requisito Obrigatório 2 da Atividade Avaliativa.
 */
public class CamposObrigatoriosValidador implements Validador<String> {

    private final String nomeCampo;
    private final String valor;

    public CamposObrigatoriosValidador(String nomeCampo, String valor) {
        this.nomeCampo = nomeCampo;
        this.valor = valor;
    }

    @Override
    public boolean validar(String valorAtual) {
        return this.valor != null && !this.valor.trim().isEmpty();
    }

    @Override
    public String getMensagemErro() {
        return "O campo '" + nomeCampo + "' é obrigatório e deve ser preenchido.";
    }

    @Override
    public String getValor() {
        return valor;
    }
}
