package com.template.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Utilitário para exibição de diálogos e alertas na interface gráfica JavaFX.
 */
public class DialogUtil {

    private DialogUtil() {
        // Classe utilitária, construtor privado
    }

    /**
     * Exibe um alerta do tipo ERRO para o usuário.
     *
     * @param mensagem Mensagem detalhando o erro
     */
    public static void showError(String mensagem) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    /**
     * Exibe um alerta do tipo INFORMAÇÃO para o usuário.
     *
     * @param mensagem Mensagem informativa
     */
    public static void showInfo(String mensagem) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    /**
     * Exibe um alerta do tipo AVISO/WARNING para o usuário.
     *
     * @param mensagem Mensagem de advertência
     */
    public static void showWarning(String mensagem) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Atenção");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    /**
     * Exibe um diálogo de confirmação com opções SIM/NÃO.
     *
     * @param titulo   Título da janela
     * @param mensagem Mensagem a ser confirmada
     * @return true se o usuário confirmou (OK/SIM), false caso contrário
     */
    public static boolean showConfirmation(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
