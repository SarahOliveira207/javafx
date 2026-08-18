package com.template.controller;

import com.template.exception.ValidationException;
import com.template.model.dto.CarroDTO;
import com.template.service.CarroService;
import com.template.util.DialogUtil;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

/**
 * Controller responsável exclusivamente pela interface gráfica (View / FXML) e mediação de eventos (SRP).
 * Toda a lógica de validação é delegada a CarroValidator e a lógica de negócios a CarroService.
 */
public class MainController {

    // Elementos da Interface Gráfica (FXML)
    @FXML private TableView<CarroDTO> tblCarro;
    @FXML private TableColumn<CarroDTO, Integer> colId;
    @FXML private TableColumn<CarroDTO, String> colNome;
    @FXML private TableColumn<CarroDTO, Integer> colPotencia;
    @FXML private TableColumn<CarroDTO, String> colCor;

    @FXML private TextField fieldNome;
    @FXML private TextField fieldCavalos;
    @FXML private TextField fieldCor;

    @FXML private Button buttonInserir;
    @FXML private Button buttonSalvar;
    @FXML private Button buttonExcluir;
    @FXML private Button buttonAtualizar;

    // Camada de Serviço (Lógica de Negócios)
    private final CarroService carroService;

    // Estado do veículo selecionado na tabela
    private CarroDTO carroSelecionado;

    public MainController() {
        this.carroService = new CarroService();
    }

    public MainController(CarroService carroService) {
        this.carroService = carroService;
    }

    @FXML
    private void initialize() {
        configurarColunasTabela();
        configurarSelecaoTabela();
        carregarCarros();
    }

    /**
     * Configura o mapeamento entre as colunas da TableView e os atributos do CarroDTO.
     */
    private void configurarColunasTabela() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colPotencia.setCellValueFactory(new PropertyValueFactory<>("pt_cavalos"));
        colCor.setCellValueFactory(new PropertyValueFactory<>("cor"));
    }

    /**
     * Adiciona o listener de seleção na TableView para carregar os dados nos campos de formulário.
     */
    private void configurarSelecaoTabela() {
        tblCarro.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> preencherCampos(newValue)
        );
    }

    /**
     * Carrega e atualiza os dados da tabela buscando da camada de serviço.
     */
    @FXML
    public void carregarCarros() {
        try {
            List<CarroDTO> listaCarros = carroService.listarCarros();
            tblCarro.setItems(FXCollections.observableArrayList(listaCarros));
        } catch (Exception e) {
            DialogUtil.showError("Erro ao carregar lista de veículos: " + e.getMessage());
        }
    }

    /**
     * Ação do botão Inserir / Cadastrar novo veículo.
     */
    @FXML
    private void btnInserir(ActionEvent event) {
        try {
            String nome = fieldNome.getText();
            String cavalosStr = fieldCavalos.getText();
            String cor = fieldCor.getText();

            carroService.cadastrarCarro(nome, cavalosStr, cor);
            DialogUtil.showInfo("Veículo cadastrado com sucesso!");

            limparFormulario();
            carregarCarros();
        } catch (ValidationException e) {
            DialogUtil.showWarning(e.getMessage());
        } catch (Exception e) {
            DialogUtil.showError("Erro ao cadastrar veículo: " + e.getMessage());
        }
    }

    /**
     * Ação do botão Salvar / Atualizar dados do veículo selecionado.
     */
    @FXML
    private void btnSalvar(ActionEvent event) {
        if (this.carroSelecionado == null) {
            DialogUtil.showWarning("Por favor, selecione um veículo na tabela para editar.");
            return;
        }

        try {
            String nome = fieldNome.getText();
            String cavalosStr = fieldCavalos.getText();
            String cor = fieldCor.getText();

            carroService.atualizarCarro(carroSelecionado.getId(), nome, cavalosStr, cor);
            DialogUtil.showInfo("Veículo atualizado com sucesso!");

            limparFormulario();
            carregarCarros();
        } catch (ValidationException e) {
            DialogUtil.showWarning(e.getMessage());
        } catch (Exception e) {
            DialogUtil.showError("Erro ao atualizar veículo: " + e.getMessage());
        }
    }

    /**
     * Ação do botão Excluir veículo.
     */
    @FXML
    private void btnExcluir(ActionEvent event) {
        if (this.carroSelecionado == null) {
            DialogUtil.showWarning("Por favor, selecione um veículo na tabela para excluir.");
            return;
        }

        boolean confirmacao = DialogUtil.showConfirmation(
                "Confirmar Exclusão",
                "Deseja realmente excluir o veículo '" + carroSelecionado.getNome() + "' (ID: " + carroSelecionado.getId() + ")?"
        );

        if (confirmacao) {
            try {
                carroService.excluirCarro(this.carroSelecionado);
                DialogUtil.showInfo("Veículo excluído com sucesso!");

                limparFormulario();
                carregarCarros();
            } catch (ValidationException e) {
                DialogUtil.showWarning(e.getMessage());
            } catch (Exception e) {
                DialogUtil.showError("Erro ao excluir veículo: " + e.getMessage());
            }
        }
    }

    /**
     * Ação do botão Atualizar / Recarregar tabela.
     */
    @FXML
    private void btnAtualizar(ActionEvent event) {
        carregarCarros();
    }

    /**
     * Preenche os campos do formulário quando um item é selecionado na tabela.
     *
     * @param carro Objeto selecionado na tabela
     */
    private void preencherCampos(CarroDTO carro) {
        this.carroSelecionado = carro;
        if (carro != null) {
            fieldNome.setText(carro.getNome());
            fieldCavalos.setText(String.valueOf(carro.getPt_cavalos()));
            fieldCor.setText(carro.getCor());
        }
    }

    /**
     * Limpa os campos de entrada e remove a seleção da tabela.
     */
    @FXML
    private void btnLimpar(ActionEvent event) {
        limparFormulario();
    }

    /**
     * Método auxiliar interno para resetar o formulário.
     */
    private void limparFormulario() {
        this.carroSelecionado = null;
        fieldNome.clear();
        fieldCavalos.clear();
        fieldCor.clear();
        tblCarro.getSelectionModel().clearSelection();
    }
}