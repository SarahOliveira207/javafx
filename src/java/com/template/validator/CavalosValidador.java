package com.template.validator;

/**
 * Validador específico para campos numéricos de cavalos/potência (OCP / LSP).
 */
public class CavalosValidador implements Validador<String> {

    private final String nomeCampo;
    private final String valor;
    private String mensagemErro;

    public CavalosValidador(String nomeCampo, String valor) {
        this.nomeCampo = nomeCampo;
        this.valor = valor;
        this.mensagemErro = "O campo '" + nomeCampo + "' deve ser preenchido.";
    }

    @Override
    public boolean validar(String valorAtual) {
        if (this.valor == null || this.valor.trim().isEmpty()) {
            this.mensagemErro = "O campo '" + nomeCampo + "' deve ser preenchido.";
            return false;
        }
        try {
            int cavalos = Integer.parseInt(this.valor.trim());
            if (cavalos <= 0) {
                this.mensagemErro = "O campo '" + nomeCampo + "' deve ser um número maior que zero.";
                return false;
            }
            if (cavalos > 5000) {
                this.mensagemErro = "O campo '" + nomeCampo + "' possui um valor inválido (máx: 5000 cv).";
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
