package com.template.main;

import com.template.controller.MainController;
import com.template.validator.CarroValidador;
import com.template.validator.ICarroValidador;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * Classe principal da aplicação JavaFX.
 * Configura o carregamento da View (FXML) e utiliza a fábrica de controladores (ControllerFactory)
 * para realizar a Injeção de Dependência do ICarroValidador no MainController (DIP - SOLID).
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // 1. Instancia as dependências concretas (DIP - Slide 32)
        ICarroValidador carroValidador = new CarroValidador();

        // 2. Localiza o arquivo FXML da View
        URL fxmlLocation = getClass().getResource("/com/template/main.fxml");
        if (fxmlLocation == null) {
            System.err.println("Erro: main.fxml não encontrado. Verifique o caminho.");
            return;
        }

        FXMLLoader loader = new FXMLLoader(fxmlLocation);

        // 3. Configura a fábrica de controladores para injetar o validador no construtor do MainController
        loader.setControllerFactory(controllerClass -> {
            if (controllerClass == MainController.class) {
                return new MainController(carroValidador);
            }
            try {
                return controllerClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Scene scene = new Scene(loader.load(), 820, 600);
        stage.setTitle("Gerenciamento de Veículos - MVC & SOLID (DIP / OCP / LSP)");
        stage.setMinWidth(700);
        stage.setMinHeight(520);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}