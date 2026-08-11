package com.template.controller;

import com.template.model.dao.CarroDAO;
import com.template.model.dto.CarroDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import jdk.internal.agent.Agent;

import static com.template.util.DialogUtil.*;

public class services {

    @FXML
    private void btnSalvar(ActionEvent event) { // Este método atualiza os dados
        CarroDTO carroSelecionado = new CarroDTO();
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
        CarroDTO carroSelecionado = new CarroDTO();
        if (carroSelecionado != null) {
            CarroDAO objCarroDAO = new CarroDAO();
            objCarroDAO.excluirCarro(carroSelecionado);

            btnLimpar();
            carregarCarros();
        }
    }

    private void btnCarregar(CarroDTO objCarroDTO) {
        CarroDTO carroSelecionado = new CarroDTO();
        if (objCarroDTO != null) {
            this.carroSelecionado = objCarroDTO;
            fieldNome.setText(objCarroDTO.getNome());
            fieldCavalos.setText(String.valueOf(objCarroDTO.getPt_cavalos())); // Corrigido: puxar os cavalos em vez do ID
            fieldCor.setText(objCarroDTO.getCor());
        }
    }

    private void btnLimpar() {
        CarroDTO carroSelecionado = new CarroDTO();
        fieldNome.clear();
        fieldCavalos.clear();
        fieldCor.clear();
        this.carroSelecionado = null;
    }
}
