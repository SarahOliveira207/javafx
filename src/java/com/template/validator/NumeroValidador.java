package com.template.validator;

/**
 * Validador para campos que requerem números inteiros positivos (como cavalos/potência, quantidade, ano, etc.).
 * Atende ao Requisito Obrigatório 2 da Atividade Avaliativa.
 */
public class NumeroValidador implements Validador<String> {

    private final String nomeCampo;
    private final String valor;
    private String mensagemErro;

    public NumeroValidador(String nomeCampo, String valor) {
        this.nomeCampo = nomeCampo;
        this.valor = valor;
        this.mensagemErro = "O campo '" + nomeCampo + "' deve ser preenchido.";
    }

    @Override
    public boolean validar(String valorAtual) {
        if (this.valor == null || this.valor.trim().isEmpty()) {
            this.mensagemErro = "O campo '" + nomeCampo + "' é obrigatório.";
            return false;
        }
        try {
            int numero = Integer.parseInt(this.valor.trim());
            if (numero <= 0) {
                this.mensagemErro = "O campo '" + nomeCampo + "' deve ser um número maior que zero.";
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            this.mensagemErro = "O campo '" + nomeCampo + "' deve conter apenas números inteiros válidos.";
            return false;
        }
    }

    @Override
    public String getMensagemErro() {
        return mensagemErro;
    }

    @Override
    public String getValor() {
        return valor;
    }
}
