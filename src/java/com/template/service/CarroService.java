package com.template.service;

import com.template.exception.ValidationException;
import com.template.model.CarroDAO;
import com.template.model.dto.CarroDTO;
import com.template.validator.CarroValidador;
import com.template.validator.ICarroValidador;

import java.util.ArrayList;

/**
 * Classe de Serviço (Service) responsável por centralizar a lógica de negócios,
 * conversão de dados e orquestração com o DAO (SRP e DIP).
 */
public class CarroService {

    private final CarroDAO carroDAO;
    private final ICarroValidador carroValidador;

    public CarroService() {
        this(new CarroDAO(), new CarroValidador());
    }

    public CarroService(CarroDAO carroDAO, ICarroValidador carroValidador) {
        this.carroDAO = carroDAO;
        this.carroValidador = carroValidador;
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
     * Cadastra um novo veículo após validação prévia.
     *
     * @param nome       Nome ou modelo do veículo
     * @param cavalosStr Quantidade de cavalos em formato texto
     * @param cor        Cor do veículo
     * @return O objeto CarroDTO criado e persistido
     */
    public CarroDTO cadastrarCarro(String nome, String cavalosStr, String cor) {
        int cavalos = Integer.parseInt(cavalosStr.trim());
        CarroDTO novoCarro = new CarroDTO(nome.trim(), cavalos, cor.trim());
        carroDAO.inserirCarro(novoCarro);
        return novoCarro;
    }

    /**
     * Atualiza um veículo existente no banco de dados.
     *
     * @param id         Identificador do veículo a ser atualizado
     * @param nome       Nome ou modelo do veículo
     * @param cavalosStr Quantidade de cavalos em formato texto
     * @param cor        Cor do veículo
     * @return O objeto CarroDTO atualizado
     */
    public CarroDTO atualizarCarro(int id, String nome, String cavalosStr, String cor) {
        if (id <= 0) {
            throw new ValidationException("Identificador de veículo inválido para atualização.");
        }
        int cavalos = Integer.parseInt(cavalosStr.trim());
        CarroDTO carroAtualizado = new CarroDTO(id, nome.trim(), cavalos, cor.trim());
        carroDAO.salvarCarro(carroAtualizado);
        return carroAtualizado;
    }

    /**
     * Exclui um veículo através do DAO.
     *
     * @param carro Objeto CarroDTO a ser excluído
     */
    public void excluirCarro(CarroDTO carro) {
        if (carro == null || carro.getId() <= 0) {
            throw new ValidationException("Selecione um veículo na tabela para realizar a exclusão.");
        }
        carroDAO.excluirCarro(carro);
    }
}
