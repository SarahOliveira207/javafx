package com.template.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class DialogUtil {
    public static void showError (String mensagem) {
        Alert alert = new Alert (AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static void showInfo (String mensagem){
        Alert alert = new Alert (AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static void showSusses (String mensagem) {
        Alert alert = new Alert (AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText("Este ~e um alerta de infomação");
        alert.showAndWait();
    }

    public static void showConfim (String mensagem) {
        boolean changeConfirmed;
        Alert alert = new Alert (AlertType.CONFIRMATION);
        alert.setTitle ("Confirmar alterações");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.isPresent() && buttonType.get().equals(ButtonType.OK)) {
            changeConfirmed = true;
        }

    }

}
