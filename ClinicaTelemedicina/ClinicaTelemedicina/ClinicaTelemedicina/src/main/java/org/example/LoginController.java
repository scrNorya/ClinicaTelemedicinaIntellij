package org.example;

import java.io.IOException;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.utils.JsonUtils;

public class LoginController {
	
	@FXML private TextField username;
	
	@FXML private PasswordField password;
	
	
	public void login(ActionEvent e) {
		Map<String, Object> data = JsonUtils.readValues(LoginController.class.getResource("Recepcionista.json").toString().substring(6));

	}
	
	public void switchToMedico(ActionEvent e) throws IOException {
		App.setRoot("Agenda");
	}
	
	public void switchToRecepcionista(ActionEvent e) throws IOException {
		App.setRoot("Calendario");
	}
}
