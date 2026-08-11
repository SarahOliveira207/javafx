package com.template.controller;

import com.template.model.dao.CarroDAO;
import com.template.model.dto.CarroDTO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

import java.util.ArrayList;

public class validator {

    @FXML
    private void carregarCarros() {
        CarroDAO objCarroDAO = new CarroDAO();
        ArrayList<CarroDTO> listaCarros = objCarroDAO.listarCarro();
        tblCarro.setItems(FXCollections.observableArrayList(listaCarros));
    }
}
