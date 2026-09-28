package com.template.controller;

import com.template.model.dto.CarroDTO;
import com.template.service.CarroService;
import com.template.util.DialogUtil;
import com.template.validator.ICarroValidador;
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
 * Controller responsável pela interface gráfica do cadastro de veículos (MVC + FXML).
 * Atende aos Requisitos 5 e 6: utiliza a interface ICarroValidador (e não a classe concreta)
 * e recebe sua dependência via Injeção de Dependência através da Fábrica de Controladores.
 */
public class MainController {

    // Dependência da interface de validação injetada externamente (DIP - SOLID / Requisito 5 e 6)
    private final ICarroValidador carroValidador;

    // Camada de Serviço para regras de negócio e persistência (SRP)
    private final CarroService carroService;

    // Elementos da interface gráfica (FXML)
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

    // Referência ao veículo selecionado na tabela
    private CarroDTO carroSelecionado;

    /**
     * Construtor com Injeção de Dependência da interface ICarroValidador (Requisito 6).
     * O Controller NÃO instancia diretamente a classe concreta CarroValidador.
     *
     * @param carroValidador Implementação de ICarroValidador fornecida pela fábrica
     */
    public MainController(ICarroValidador carroValidador) {
        this.carroValidador = carroValidador;
        this.carroService = new CarroService();
    }

    @FXML
    private void initialize() {
        configurarColunasTabela();
        configurarSelecaoTabela();
        carregarCarros();
    }

    private void configurarColunasTabela() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colPotencia.setCellValueFactory(new PropertyValueFactory<>("pt_cavalos"));
        colCor.setCellValueFactory(new PropertyValueFactory<>("cor"));
    }

    private void configurarSelecaoTabela() {
        tblCarro.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> preencherCampos(newValue)
        );
    }

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
     * Ação de Cadastro (Inserir).
     * Atende ao Requisito 5: validação realizada via ICarroValidador antes do cadastro.
     */
    @FXML
    private void btnInserir(ActionEvent event) {
        // Validação obrigatória no cadastro através da interface ICarroValidador
        if (!carroValidador.validarCarro(fieldNome.getText(), fieldCavalos.getText(), fieldCor.getText())) {
            return; // Se a validação falhar, o fluxo é interrompido
        }

        try {
            String nome = fieldNome.getText();
            String cavalosStr = fieldCavalos.getText();
            String cor = fieldCor.getText();

            carroService.cadastrarCarro(nome, cavalosStr, cor);
            DialogUtil.showInfo("Veículo cadastrado com sucesso!");

            limparFormulario();
            carregarCarros();
        } catch (Exception e) {
            DialogUtil.showError("Erro ao cadastrar veículo: " + e.getMessage());
        }
    }

    /**
     * Ação de Atualização (Salvar).
     * Atende ao Requisito 5: validação realizada via ICarroValidador antes da atualização.
     */
    @FXML
    private void btnSalvar(ActionEvent event) {
        if (this.carroSelecionado == null) {
            DialogUtil.showWarning("Por favor, selecione um veículo na tabela para editar.");
            return;
        }

        // Validação obrigatória na atualização através da interface ICarroValidador
        if (!carroValidador.validarCarro(fieldNome.getText(), fieldCavalos.getText(), fieldCor.getText())) {
            return; // Se a validação falhar, o fluxo é interrompido
        }

        try {
            String nome = fieldNome.getText();
            String cavalosStr = fieldCavalos.getText();
            String cor = fieldCor.getText();

            carroService.atualizarCarro(carroSelecionado.getId(), nome, cavalosStr, cor);
            DialogUtil.showInfo("Veículo atualizado com sucesso!");

            limparFormulario();
            carregarCarros();
        } catch (Exception e) {
            DialogUtil.showError("Erro ao atualizar veículo: " + e.getMessage());
        }
    }

    /**
     * Ação de Exclusão de veículo.
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
            } catch (Exception e) {
                DialogUtil.showError("Erro ao excluir veículo: " + e.getMessage());
            }
        }
    }

    @FXML
    private void btnAtualizar(ActionEvent event) {
        carregarCarros();
    }

    private void preencherCampos(CarroDTO carro) {
        this.carroSelecionado = carro;
        if (carro != null) {
            fieldNome.setText(carro.getNome());
            fieldCavalos.setText(String.valueOf(carro.getPt_cavalos()));
            fieldCor.setText(carro.getCor());
        }
    }

    @FXML
    private void btnLimpar(ActionEvent event) {
        limparFormulario();
    }

    private void limparFormulario() {
        this.carroSelecionado = null;
        fieldNome.clear();
        fieldCavalos.clear();
        fieldCor.clear();
        tblCarro.getSelectionModel().clearSelection();
    }
}