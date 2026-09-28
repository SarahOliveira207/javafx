package com.template.validator;

/**
 * Validador para garantir o preenchimento de campos de texto (compatibilidade).
 */
public class CampoObrigatorioValidador extends CamposObrigatoriosValidador {

    public CampoObrigatorioValidador(String nomeCampo, String valor) {
        super(nomeCampo, valor);
    }
}
