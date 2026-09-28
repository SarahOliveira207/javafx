package com.template.validator;

/**
 * Interface que define o contrato de validação para a entidade Carro (Princípio da Inversão de Dependência - DIP e Segregação de Interfaces - ISP).
 */
public interface ICarroValidador {

    /**
     * Valida todos os campos de um carro para cadastro ou atualização.
     *
     * @param nome     Nome ou modelo do carro
     * @param cavalos  Potência em cavalos (formato texto)
     * @param cor      Cor do veículo
     * @return true se todos os campos forem válidos; false caso algum falhe
     */
    boolean validarCarro(String nome, String cavalos, String cor);
}
