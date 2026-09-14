package com.template.controller;

import com.template.model.dto.CarroDTO;
import com.template.service.CarroService;
import com.template.util.DialogUtil;
import com.template.validator.CarroValidador;
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
 * Controller responsável pela interface gráfica JavaFX.
 * Aplica o Princípio da Inversão de Dependência (DIP - SOLID) ao receber a interface ICarroValidador no construtor.
 */
public class MainController {

    // Dependência de abstração injetada via construtor (DIP)
    private final ICarroValidador carroValidador;

    // Camada de Serviço para regras de negócio e persistência
    private final CarroService carroService;

    // Elementos visuais (FXML)
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

    // Estado do veículo selecionado na tabela
    private CarroDTO carroSelecionado;

    /**
     * Construtor com Injeção de Dependência (DIP) recebendo a interface ICarroValidador.
     *
     * @param carroValidador Objeto validador implementando ICarroValidador
     */
    public MainController(ICarroValidador carroValidador) {
        this.carroValidador = carroValidador;
        this.carroService = new CarroService();
    }

    /**
     * Construtor padrão para compatibilidade.
     */
    public MainController() {
        this(new CarroValidador());
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
     * Ação do botão Inserir / Cadastrar novo veículo.
     * Executa a validação através do objeto ICarroValidador antes de prosseguir com a lógica de negócio.
     */
    @FXML
    private void btnInserir(ActionEvent event) {
        // Validação através da interface ICarroValidador (DIP)
        if (!carroValidador.validarCarro(fieldNome.getText(), fieldCavalos.getText(), fieldCor.getText())) {
            return; // Se a validação falhar, para por aqui
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
     * Ação do botão Salvar / Atualizar dados do veículo selecionado.
     */
    @FXML
    private void btnSalvar(ActionEvent event) {
        if (this.carroSelecionado == null) {
            DialogUtil.showWarning("Por favor, selecione um veículo na tabela para editar.");
            return;
        }

        // Validação através da interface ICarroValidador (DIP)
        if (!carroValidador.validarCarro(fieldNome.getText(), fieldCavalos.getText(), fieldCor.getText())) {
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
        } catch (Exception e) {
            DialogUtil.showError("Erro ao atualizar veículo: " + e.getMessage());
        }
    }

    /**
     * Ação do botão Excluir veículo selecionado.
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