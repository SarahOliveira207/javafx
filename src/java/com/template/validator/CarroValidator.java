package com.template.validator;

import com.template.exception.ValidationException;
import com.template.model.dto.CarroDTO;

import java.util.regex.Pattern;

/**
 * Classe responsável exclusivamente pela validação de dados de entrada e regras de formato (SRP).
 */
public class CarroValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    /**
     * Valida se todos os campos obrigatórios para cadastro/atualização foram preenchidos.
     *
     * @param nome       Nome ou modelo do carro
     * @param cavalosStr Texto contendo a potência em cavalos
     * @param cor        Cor do veículo
     * @throws ValidationException caso algum campo esteja nulo ou vazio
     */
    public void validarCampos(String nome, String cavalosStr, String cor) throws ValidationException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new ValidationException("O campo 'Nome/Modelo' é obrigatório.");
        }
        if (nome.trim().length() < 2) {
            throw new ValidationException("O campo 'Nome/Modelo' deve conter pelo menos 2 caracteres.");
        }
        if (cavalosStr == null || cavalosStr.trim().isEmpty()) {
            throw new ValidationException("O campo 'Cavalos/Potência' é obrigatório.");
        }
        if (cor == null || cor.trim().isEmpty()) {
            throw new ValidationException("O campo 'Cor' é obrigatório.");
        }
    }

    /**
     * Valida e converte a string de cavalos para um valor numérico inteiro válido.
     *
     * @param cavalosStr String representando a quantidade de cavalos
     * @return Valor numérico inteiro correspondente
     * @throws ValidationException caso o valor não seja numérico ou seja <= 0
     */
    public int validarCavalos(String cavalosStr) throws ValidationException {
        if (cavalosStr == null || cavalosStr.trim().isEmpty()) {
            throw new ValidationException("Informe o valor da potência em cavalos.");
        }
        try {
            int cavalos = Integer.parseInt(cavalosStr.trim());
            if (cavalos <= 0) {
                throw new ValidationException("A potência (cavalos) deve ser um número maior que zero.");
            }
            if (cavalos > 5000) {
                throw new ValidationException("A potência informada é inválida (valor máximo: 5000 cv).");
            }
            return cavalos;
        } catch (NumberFormatException e) {
            throw new ValidationException("O campo 'Cavalos' deve conter apenas números inteiros válidos.");
        }
    }

    /**
     * Valida se uma instância de CarroDTO está consistente antes de salvar.
     *
     * @param carro Objeto CarroDTO
     * @throws ValidationException caso o objeto ou seus campos sejam inválidos
     */
    public void validarCarro(CarroDTO carro) throws ValidationException {
        if (carro == null) {
            throw new ValidationException("Nenhum carro foi informado para a operação.");
        }
        validarCampos(carro.getNome(), String.valueOf(carro.getPt_cavalos()), carro.getCor());
        if (carro.getPt_cavalos() <= 0) {
            throw new ValidationException("A potência (cavalos) deve ser maior que zero.");
        }
    }

    /**
     * Valida o formato de e-mail (atendendo ao requisito de validação reutilizável de entrada).
     *
     * @param email Endereço de e-mail a ser validado
     * @return true se o e-mail for válido, false caso contrário
     * @throws ValidationException caso o e-mail seja inválido
     */
    public boolean validarEmail(String email) throws ValidationException {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("O campo 'E-mail' não pode ser vazio.");
        }
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new ValidationException("O formato do e-mail informado é inválido.");
        }
        return true;
    }
}
