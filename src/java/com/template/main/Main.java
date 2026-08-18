package com.template.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        URL fxmlLocation = getClass().getResource("/com/template/main.fxml");
        if (fxmlLocation == null) {
            throw new IllegalStateException("Não foi possível localizar o arquivo FXML em /com/template/main.fxml");
        }

        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Scene scene = new Scene(loader.load(), 640, 420);

        stage.setTitle("Gerenciamento de Veículos - MVC & SOLID");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}