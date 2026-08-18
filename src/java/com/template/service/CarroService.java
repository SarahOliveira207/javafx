package com.template.service;

import com.template.exception.ValidationException;
import com.template.model.CarroDAO;
import com.template.model.dto.CarroDTO;
import com.template.validator.CarroValidator;

import java.util.ArrayList;

/**
 * Classe de Serviço (Service) responsável por centralizar a lógica de negócios,
 * orquestrar a validação e gerenciar a persistência através do DAO (SRP).
 */
public class CarroService {

    private final CarroDAO carroDAO;
    private final CarroValidator carroValidator;

    public CarroService() {
        this(new CarroDAO(), new CarroValidator());
    }

    public CarroService(CarroDAO carroDAO, CarroValidator carroValidator) {
        this.carroDAO = carroDAO;
        this.carroValidator = carroValidator;
    }

    /**
     * Recupera todos os veículos cadastrados.
     *
     * @return Lista com todos os carros
     */
    public ArrayList<CarroDTO> listarCarros() {
        return carroDAO.listarCarro();
    }

    /**
     * Executa a regra de negócio para cadastro de um novo veículo:
     * 1. Valida os campos de entrada via CarroValidator
     * 2. Converte os dados tipados
     * 3. Cria a instância de CarroDTO
     * 4. Persiste no banco de dados através do CarroDAO
     *
     * @param nome       Nome ou modelo do veículo
     * @param cavalosStr Quantidade de cavalos em formato texto
     * @param cor        Cor do veículo
     * @return O objeto CarroDTO criado e validado
     * @throws ValidationException caso ocorra falha de validação dos dados
     */
    public CarroDTO cadastrarCarro(String nome, String cavalosStr, String cor) throws ValidationException {
        carroValidator.validarCampos(nome, cavalosStr, cor);
        int cavalos = carroValidator.validarCavalos(cavalosStr);

        CarroDTO novoCarro = new CarroDTO(nome.trim(), cavalos, cor.trim());
        carroDAO.inserirCarro(novoCarro);
        return novoCarro;
    }

    /**
     * Executa a regra de negócio para atualização de um veículo existente:
     * 1. Verifica se o ID do veículo é válido
     * 2. Valida os dados de entrada via CarroValidator
     * 3. Converte os dados tipados
     * 4. Atualiza o registro no banco via CarroDAO
     *
     * @param id         Identificador do veículo a ser atualizado
     * @param nome       Nome ou modelo do veículo
     * @param cavalosStr Quantidade de cavalos em formato texto
     * @param cor        Cor do veículo
     * @return O objeto CarroDTO atualizado
     * @throws ValidationException caso os dados ou o ID sejam inválidos
     */
    public CarroDTO atualizarCarro(int id, String nome, String cavalosStr, String cor) throws ValidationException {
        if (id <= 0) {
            throw new ValidationException("Identificador de veículo inválido para atualização.");
        }
        carroValidator.validarCampos(nome, cavalosStr, cor);
        int cavalos = carroValidator.validarCavalos(cavalosStr);

        CarroDTO carroAtualizado = new CarroDTO(id, nome.trim(), cavalos, cor.trim());
        carroDAO.salvarCarro(carroAtualizado);
        return carroAtualizado;
    }

    /**
     * Executa a regra de negócio para exclusão de um veículo:
     * 1. Verifica se o carro selecionado é válido
     * 2. Remove o registro através do CarroDAO
     *
     * @param carro Objeto CarroDTO a ser excluído
     * @throws ValidationException caso o veículo não tenha sido selecionado
     */
    public void excluirCarro(CarroDTO carro) throws ValidationException {
        if (carro == null || carro.getId() <= 0) {
            throw new ValidationException("Selecione um veículo na tabela para realizar a exclusão.");
        }
        carroDAO.excluirCarro(carro);
    }
}
