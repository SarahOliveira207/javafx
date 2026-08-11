package com.template.controller;

import com.template.model.dao.CarroDAO;
import com.template.model.dto.CarroDTO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import static util.DialogUtil.*;
import validator.UsuariValidator;

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
    private void btnInserir(ActionEvent event) {
        CarroDTO objCarroDTO = new CarroDTO();
        objCarroDTO.setNome(fieldNome.getText());
        objCarroDTO.setPt_cavalos(Integer.parseInt(fieldCavalos.getText()));
        objCarroDTO.setCor(fieldCor.getText());

        services

        /*CarroDAO objCarroDAO = new CarroDAO();
        objCarroDAO.inserirCarro(objCarroDTO);*/


        btnLimpar();
        carregarCarros();
    }


}