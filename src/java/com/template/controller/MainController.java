package com.template.controller;

import com.template.model.dto.CarroDTO;
import com.template.service.CarroService;
import com.template.util.DialogUtil;
import com.template.validator.ICarroValidador;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.List;

/**
 * Controller responsável pela interface gráfica do cadastro de veículos (MVC + FXML).
 * Atende aos Requisitos 5 e 6: utiliza a interface ICarroValidador (e não a classe concreta)
 * e recebe sua dependência via Injeção de Dependência através da Fábrica de Controladores.
 */
public class MainController {

    // Classes CSS usadas no label de mensagens (definidas em style.css)
    private static final String CSS_SUCESSO = "mensagem-sucesso";
    private static final String CSS_ERRO = "mensagem-erro";

    // Quantidade máxima de dígitos aceitos no campo de cavalos
    private static final int MAX_DIGITOS_CAVALOS = 4;

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
    @FXML private Button buttonLimpar;

    @FXML private Label lblMensagem;

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
        configurarCampoNumerico();
        configurarTeclado();
        carregarCarros();

        // Cursor no primeiro campo ao abrir a tela
        focarPrimeiroCampo();
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

    /**
     * Impede a digitação (ou colagem) de qualquer caractere que não seja dígito
     * no campo de cavalos, limitando também a quantidade de dígitos.
     */
    private void configurarCampoNumerico() {
        fieldCavalos.setTextFormatter(new TextFormatter<String>(alteracao -> {
            String novoTexto = alteracao.getControlNewText();
            if (novoTexto.matches("\\d{0," + MAX_DIGITOS_CAVALOS + "}")) {
                return alteracao;
            }
            return null; // Rejeita a alteração
        }));
    }

    /**
     * Atalhos de teclado para facilitar o uso só com teclado (TAB + ENTER):
     * - ENTER em qualquer campo grava (Salvar se houver veículo selecionado, senão Inserir);
     * - ENTER sobre um botão focado aciona o botão;
     * - ESC limpa o formulário.
     */
    private void configurarTeclado() {
        for (TextField campo : List.of(fieldNome, fieldCavalos, fieldCor)) {
            campo.setOnAction(e -> {
                if (carroSelecionado != null) {
                    buttonSalvar.fire();
                } else {
                    buttonInserir.fire();
                }
            });
        }

        for (Button botao : List.of(buttonInserir, buttonSalvar, buttonExcluir, buttonAtualizar, buttonLimpar)) {
            botao.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ENTER) {
                    botao.fire();
                    e.consume();
                }
            });
        }

        // A cena ainda não existe durante o initialize(); registra o ESC quando ela estiver disponível
        fieldNome.sceneProperty().addListener((obs, cenaAntiga, cena) -> {
            if (cena != null) {
                cena.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
                    if (e.getCode() == KeyCode.ESCAPE) {
                        limparFormulario();
                        limparMensagem();
                    }
                });
            }
        });
    }

    @FXML
    public void carregarCarros() {
        try {
            List<CarroDTO> listaCarros = carroService.listarCarros();
            tblCarro.setItems(FXCollections.observableArrayList(listaCarros));
        } catch (Exception e) {
            mostrarErro("Erro ao carregar lista de veículos: " + e.getMessage());
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
            mostrarErro(carroValidador.getMensagemErro());
            focarCampoInvalido();
            return; // Se a validação falhar, o fluxo é interrompido
        }

        try {
            String nome = fieldNome.getText();
            String cavalosStr = fieldCavalos.getText();
            String cor = fieldCor.getText();

            carroService.cadastrarCarro(nome, cavalosStr, cor);

            limparFormulario();
            carregarCarros();
            mostrarSucesso("Veículo \"" + nome.trim() + "\" cadastrado com sucesso!");
        } catch (Exception e) {
            mostrarErro("Erro ao cadastrar veículo: " + e.getMessage());
            focarPrimeiroCampo();
        }
    }

    /**
     * Ação de Atualização (Salvar).
     * Atende ao Requisito 5: validação realizada via ICarroValidador antes da atualização.
     */
    @FXML
    private void btnSalvar(ActionEvent event) {
        if (this.carroSelecionado == null) {
            mostrarErro("Selecione um veículo na tabela para editar.");
            focarPrimeiroCampo();
            return;
        }

        // Validação obrigatória na atualização através da interface ICarroValidador
        if (!carroValidador.validarCarro(fieldNome.getText(), fieldCavalos.getText(), fieldCor.getText())) {
            mostrarErro(carroValidador.getMensagemErro());
            focarCampoInvalido();
            return; // Se a validação falhar, o fluxo é interrompido
        }

        try {
            String nome = fieldNome.getText();
            String cavalosStr = fieldCavalos.getText();
            String cor = fieldCor.getText();

            carroService.atualizarCarro(carroSelecionado.getId(), nome, cavalosStr, cor);

            limparFormulario();
            carregarCarros();
            mostrarSucesso("Veículo \"" + nome.trim() + "\" atualizado com sucesso!");
        } catch (Exception e) {
            mostrarErro("Erro ao atualizar veículo: " + e.getMessage());
            focarPrimeiroCampo();
        }
    }

    /**
     * Ação de Exclusão de veículo.
     */
    @FXML
    private void btnExcluir(ActionEvent event) {
        if (this.carroSelecionado == null) {
            mostrarErro("Selecione um veículo na tabela para excluir.");
            focarPrimeiroCampo();
            return;
        }

        boolean confirmacao = DialogUtil.showConfirmation(
                "Confirmar Exclusão",
                "Deseja realmente excluir o veículo '" + carroSelecionado.getNome() + "' (ID: " + carroSelecionado.getId() + ")?"
        );

        if (confirmacao) {
            try {
                String nome = carroSelecionado.getNome();
                carroService.excluirCarro(this.carroSelecionado);

                limparFormulario();
                carregarCarros();
                mostrarSucesso("Veículo \"" + nome + "\" excluído com sucesso!");
            } catch (Exception e) {
                mostrarErro("Erro ao excluir veículo: " + e.getMessage());
            }
        }
        focarPrimeiroCampo();
    }

    @FXML
    private void btnAtualizar(ActionEvent event) {
        carregarCarros();
        focarPrimeiroCampo();
    }

    private void preencherCampos(CarroDTO carro) {
        this.carroSelecionado = carro;
        if (carro != null) {
            fieldNome.setText(carro.getNome());
            fieldCavalos.setText(String.valueOf(carro.getPt_cavalos()));
            fieldCor.setText(carro.getCor());
            limparMensagem();
        }
    }

    @FXML
    private void btnLimpar(ActionEvent event) {
        limparFormulario();
        limparMensagem();
    }

    private void limparFormulario() {
        this.carroSelecionado = null;
        fieldNome.clear();
        fieldCavalos.clear();
        fieldCor.clear();
        tblCarro.getSelectionModel().clearSelection();
        focarPrimeiroCampo();
    }

    // ---------------------------------------------------------------
    // Foco (cursor)
    // ---------------------------------------------------------------

    /**
     * Posiciona o cursor no primeiro campo do formulário.
     * Usa Platform.runLater para garantir que o foco seja aplicado depois
     * que o JavaFX terminar de processar o clique/tecla atual.
     */
    private void focarPrimeiroCampo() {
        Platform.runLater(() -> {
            fieldNome.requestFocus();
            fieldNome.end();
        });
    }

    /**
     * Após um erro de validação, leva o cursor direto ao campo que precisa ser corrigido.
     */
    private void focarCampoInvalido() {
        TextField campo;
        if (estaVazio(fieldNome)) {
            campo = fieldNome;
        } else if (estaVazio(fieldCavalos) || Integer.parseInt(fieldCavalos.getText().trim()) <= 0) {
            campo = fieldCavalos;
        } else if (estaVazio(fieldCor)) {
            campo = fieldCor;
        } else {
            campo = fieldNome;
        }
        Platform.runLater(() -> {
            campo.requestFocus();
            campo.selectAll();
        });
    }

    private boolean estaVazio(TextField campo) {
        return campo.getText() == null || campo.getText().trim().isEmpty();
    }

    // ---------------------------------------------------------------
    // Mensagens no label (verde = sucesso, vermelho = erro)
    // ---------------------------------------------------------------

    private void mostrarSucesso(String mensagem) {
        exibirMensagem("✔  " + mensagem, CSS_SUCESSO);
    }

    private void mostrarErro(String mensagem) {
        exibirMensagem("✖  " + mensagem, CSS_ERRO);
    }

    private void exibirMensagem(String texto, String classeCss) {
        lblMensagem.getStyleClass().removeAll(CSS_SUCESSO, CSS_ERRO);
        lblMensagem.getStyleClass().add(classeCss);
        lblMensagem.setText(texto);
        lblMensagem.setVisible(true);
    }

    private void limparMensagem() {
        lblMensagem.setText("");
        lblMensagem.setVisible(false);
        lblMensagem.getStyleClass().removeAll(CSS_SUCESSO, CSS_ERRO);
    }
}
