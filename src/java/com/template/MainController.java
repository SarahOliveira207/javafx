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

public class MainController
{
    @FXML private TableView tblCarro;
    @FXML private Button buttonSalvar;
    @FXML private Button buttonInserir;
    @FXML private Button buttonExcluir;
    @FXML private Button buttonAtualizar;
    @FXML private TextField fieldNome;
    @FXML private TextField fieldCavalos;
    @FXML private TextField fieldCor;
    @FXML private TableColumn<CarroDTO, String> colId;
    @FXML private TableColumn<CarroDTO, String> colModelo;
    @FXML private TableColumn<CarroDTO, Integer> colPotencia;
    @FXML private TableColumn<CarroDTO, String> colCor;
   // private Object objCarroDTO;

    @FXML
    private void initialize()
    {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colPotencia.setCellValueFactory(new PropertyValueFactory<>("cavalos"));
        colCor.setCellValueFactory(new PropertyValueFactory<>("cor"));
        carregarCarros();
    }

    @FXML
    private void carregarCarros(){
        CarroDAO objCarroDAO = new CarroDAO();
        ArrayList<CarroDTO> listaCarros = objCarroDAO.selecionarCarro();
        tblCarro.setItems(FXCollections.observableArrayList(listaCarros));
    }

    @FXML
    private void btnCadastrar(ActionEvent event){
        String nome = fieldNome.getText();
        int cavalos = Integer.parseInt(fieldCavalos.getText());
        String cor = fieldCor.getText();

        CarroDTO objCarroDTO = new CarroDTO();
        objCarroDTO.setNome(nome);
        objCarroDTO.setPt_cavalos(cavalos);
        objCarroDTO.setCor(cor);

        CarroDAO objCarroDAO = new CarroDAO();
        objCarroDAO.inserirCarro(objCarroDTO);

        carregarCarros();
    }

    @FXML
    private void btnInserir(ActionEvent event) {
        // 1. Resgata os valores dos campos
        String nome = fieldNome.getText();
        int cavalos = Integer.parseInt(fieldCavalos.getText());
        String cor = fieldCor.getText();

        // 2. Popula o DTO
        CarroDTO objCarroDTO = new CarroDTO();
        objCarroDTO.setNome(nome);
        objCarroDTO.setPt_cavalos(cavalos);
        objCarroDTO.setCor(cor);

        // 3. Chama o DAO para inserir
        CarroDAO objCarroDAO = new CarroDAO();
        objCarroDAO.inserirCarro(objCarroDTO);

        // 4. Atualiza a tabela/tela
        carregarCarros();
    }

    @FXML
    private void btnSalvar(ActionEvent event) {
        // Nota: Em muitos sistemas, "Salvar" e "Inserir" fazem a mesma coisa,
        // ou o "Salvar" funciona como um "Upsert" (insere se não existe, atualiza se existe).
        String nome = fieldNome.getText();
        int cavalos = Integer.parseInt(fieldCavalos.getText());
        String cor = fieldCor.getText();

        CarroDTO objCarroDTO = new CarroDTO();
        objCarroDTO.setNome(nome);
        objCarroDTO.setPt_cavalos(cavalos);
        objCarroDTO.setCor(cor);

        CarroDAO objCarroDAO = new CarroDAO();
        objCarroDAO.salvarCarro(objCarroDTO); // Método correspondente no seu DAO

        carregarCarros();
    }

    @FXML
    private void btnExcluir(ActionEvent event) {
        // Para excluir, geralmente precisamos apenas de um identificador único.
        // Aqui estou usando o 'modelo' como base, seguindo os campos disponíveis no seu exemplo.
        // Se o seu banco usa um 'ID' numérico, você deve pegar o ID do item selecionado na tabela.
        String modelo = fieldModelo.getText();

        CarroDTO objCarroDTO = new CarroDTO();
        objCarroDTO.setNome(modelo);

        CarroDAO objCarroDAO = new CarroDAO();
        objCarroDAO.excluirCarro(objCarroDTO);

        carregarCarros();
    }

    /*@FXML
    private void btnAtualizar(ActionEvent event){
        String modelo = fieldModelo.getText();
        int cavalos = Integer.parseInt(fieldCavalos.getText());
        String cor = fieldCor.getText();

        CarroDTO objCarroDTO = new CarroDTO();
        objCarroDTO.setNome(modelo);
        objCarroDTO.setPt_cavalos(cavalos);
        objCarroDTO.setCor(cor);

        CarroDAO objCarroDAO = new CarroDAO();
        objCarroDAO.atualizarCarro(objCarroDTO);

        carregarCarros();
    }*/

    @FXML
    private void carregarCampos(){
        CarroDTO objCarroDTO = (CarroDTO) tblCarro.getSelectionModel().getSelectedItem();

        if (null != objCarroDTO){
            fieldModelo.setText(objCarroDTO.getNome());
            fieldCavalos.setText(String.valueOf(objCarroDTO.getId()));
            fieldCor.setText(objCarroDTO.getCor());
        }
    }
}