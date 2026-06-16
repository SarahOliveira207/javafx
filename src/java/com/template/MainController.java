package com.template;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class MainController {
    @FXML private TableView<CarroDTO> tblCarro;
    @FXML private Button buttonSalvar;
    @FXML private Button buttonInserir;
    @FXML private Button buttonExcluir;
    @FXML private Button buttonAtualizar;
    @FXML private TextField fieldNome;
    @FXML private TextField fieldCavalos;
    @FXML private TextField fieldCor;
    @FXML private TextField fieldCarro;

    @FXML private TableColumn<CarroDTO, Integer> colId; // Corrigido para Integer
    @FXML private TableColumn<CarroDTO, String> colNome;
    @FXML private TableColumn<CarroDTO, Integer> colPotencia;
    @FXML private TableColumn<CarroDTO, String> colCor;


    private CarroDTO carroSelecionado;

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colPotencia.setCellValueFactory(new PropertyValueFactory<>("pt_cavalos")); // Corrigido para corresponder ao DTO
        colCor.setCellValueFactory(new PropertyValueFactory<>("cor"));
        carregarCarros();

        tblCarro.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> btnCarregar(newValue)
        );
    }

    @FXML
    private void carregarCarros() {
        CarroDAO objCarroDAO = new CarroDAO();
        ArrayList<CarroDTO> listaCarros = objCarroDAO.listarCarro();
        tblCarro.setItems(FXCollections.observableArrayList(listaCarros));
    }

    @FXML
    private void btnInserir(ActionEvent event) {
        CarroDTO objCarroDTO = new CarroDTO();
        objCarroDTO.setNome(fieldNome.getText());
        objCarroDTO.setPt_cavalos(Integer.parseInt(fieldCavalos.getText()));
        objCarroDTO.setCor(fieldCor.getText());

        CarroDAO objCarroDAO = new CarroDAO();
        objCarroDAO.inserirCarro(objCarroDTO);

        btnLimpar();
        carregarCarros();
    }

    @FXML
    private void btnSalvar(ActionEvent event) { // Este método atualiza os dados
        if (carroSelecionado != null) {
            carroSelecionado.setNome(fieldNome.getText());
            carroSelecionado.setPt_cavalos(Integer.parseInt(fieldCavalos.getText()));
            carroSelecionado.setCor(fieldCor.getText());

            CarroDAO objCarroDAO = new CarroDAO();
            objCarroDAO.salvarCarro(carroSelecionado);

            btnLimpar();
            carregarCarros();
        }
    }

    @FXML
    private void btnExcluir(ActionEvent event) {
        if (carroSelecionado != null) {
            CarroDAO objCarroDAO = new CarroDAO();
            objCarroDAO.excluirCarro(carroSelecionado);

            btnLimpar();
            carregarCarros();
        }
    }

    private void btnCarregar(CarroDTO objCarroDTO) {
        if (objCarroDTO != null) {
            this.carroSelecionado = objCarroDTO;
            fieldNome.setText(objCarroDTO.getNome());
            fieldCavalos.setText(String.valueOf(objCarroDTO.getPt_cavalos())); // Corrigido: puxar os cavalos em vez do ID
            fieldCor.setText(objCarroDTO.getCor());
        }
    }

    private void btnLimpar() {
        fieldNome.clear();
        fieldCavalos.clear();
        fieldCor.clear();
        this.carroSelecionado = null;
    }
}