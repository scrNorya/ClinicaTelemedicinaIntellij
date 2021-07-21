package org.example;

import java.io.IOException;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.RecepcionistaController;
import org.example.model.Medico;
import org.example.model.Recepcionista;
import org.example.model.Medico;

public class LoginController {
	
	@FXML private TextField username;
	
	@FXML private PasswordField password;

	private Recepcionista recepcionista = null;

	private Medico medico = null;

	private RecepcionistaController recepcionistaController = new RecepcionistaController();

	private MedicoController medicoController = new MedicoController();

	public boolean login() {
		try {
			System.out.println(username.getText());
			recepcionista = recepcionistaController.find(username.getText());
			if(recepcionista != null) {
				if(recepcionista.getSenha().equals(password.getText())) {
					switchToRecepcionista();
					return true;
				} else {
//					medicoController.generateAlert("Senha incorreta");
				}
			}
		} catch (Exception e) {
//			medicoController.generateAlert(e.getMessage());
		}
		if(recepcionista == null) {
			try {
				medico = medicoController.findByCPF(username.getText());
				if(medico != null) {
					if(medico.getSenha().equals(password.getText())) {
						switchToMedico();
						return true;
					} else {
//						medicoController.generateAlert("Senha incorreta");
					}
				}
			} catch (Exception e) {
//				medicoController.generateAlert(e.getMessage());
			}
		}
		medicoController.generateAlert("Usuário ou senha incorretos");
		return true;
	}
	
	public void switchToMedico() throws IOException {
		App.setRoot("Agenda");
	}
	
	public void switchToRecepcionista() throws IOException {
		App.setRoot("Calendario");
	}
}
