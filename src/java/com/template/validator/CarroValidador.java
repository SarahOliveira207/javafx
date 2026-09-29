package com.template.validator;

import com.template.util.DialogUtil;

import java.util.ArrayList;
import java.util.List;

public class CarroValidador implements ICarroValidador {


    @Override
    public boolean validarCarro(String nome, String cavalos, String cor) {

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
