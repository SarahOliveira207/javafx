package com.template.validator;

import com.template.util.DialogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por reunir e executar as validações necessárias para os campos do cadastro de veículos (Requisito 3).
 * Implementa a interface ICarroValidador (Requisito 4) e utiliza uma lista genérica percorrida com foreach.
 */
public class CarroValidador implements ICarroValidador {

    /**
     * Reúne os validadores em uma lista genérica e executa as validações sequencialmente utilizando foreach.
     *
     * @param nome    Nome ou modelo do veículo
     * @param cavalos Potência em cavalos
     * @param cor     Cor do veículo
     * @return true se todos os campos forem válidos; false caso algum falhe
     */
    @Override
    public boolean validarCarro(String nome, String cavalos, String cor) {
        // Lista genérica contendo os validadores (Requisito 3)
        List<Validador<String>> validadores = new ArrayList<>();

        // Armazenando as validações dos diferentes campos (CamposObrigatoriosValidador e NumeroValidador)
        validadores.add(new CamposObrigatoriosValidador("Nome/Modelo", nome));
        validadores.add(new NumeroValidador("Cavalos (Potência)", cavalos));
        validadores.add(new CamposObrigatoriosValidador("Cor", cor));

        // Estrutura foreach obrigatória para percorrer os validadores (Requisito 3)
        for (Validador<String> validador : validadores) {
            // Utilizando os métodos definidos pela interface Validador (validar, getValor, getMensagemErro)
            if (!validador.validar(validador.getValor())) {
                DialogUtil.showWarning(validador.getMensagemErro());
                return false; // Interrompe na primeira validação que falhar
            }
        }

        return true; // Todos os validadores foram atendidos com sucesso
    }
}
