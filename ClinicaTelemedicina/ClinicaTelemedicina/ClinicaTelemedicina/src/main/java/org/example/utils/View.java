package org.example.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class View {

    public static void generateAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public static boolean generateConfirmationDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        return alert.getResult() == ButtonType.YES;
    }
}
