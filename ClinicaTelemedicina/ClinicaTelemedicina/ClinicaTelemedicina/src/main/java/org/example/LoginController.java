package org.example;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
	
	@FXML
	private TextField username;
	
	@FXML
	private PasswordField password;
	
	
	public void login(ActionEvent e) {
		try {
			if(username.getText().equals("")) {
				switchToRecepcionista(e);							
			} else {
				switchToMedico(e);
			}	
		} catch (Exception e2) {
			System.err.println(e2.getMessage());
		}
	}
	
	public void switchToMedico(ActionEvent e) throws IOException {
		App.setRoot("Agenda");
	}
	
	public void switchToRecepcionista(ActionEvent e) throws IOException {
		App.setRoot("Calendario");
	}
}
