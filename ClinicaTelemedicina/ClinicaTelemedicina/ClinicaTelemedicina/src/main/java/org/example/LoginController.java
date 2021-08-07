package org.example;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.model.Medico;
import org.example.model.Recepcionista;
import org.example.utils.Json;
import org.example.utils.JsonType;
import org.example.utils.Validations;
import org.example.utils.View;;


public class LoginController {

	@FXML private TextField username;

	@FXML private PasswordField password;

	private Recepcionista recepcionista = null;

	private Medico medico = null;

	public void login() {

		try {
			if(Validations.isCpf(username.getText()) && Validations.isValidPassword(password.getText())) {
				recepcionista = (Recepcionista) Json.findByCPF(username.getText(), JsonType.Recepcionista);
				if(recepcionista != null  && recepcionista.getSenha().equals(password.getText())) {
					switchToRecepcionista();
				} else {
					medico = (Medico) Json.findByCPF(username.getText(), JsonType.Medico);
					if(medico != null && medico.getSenha().equals(password.getText())) {
						switchToMedico();
					} else {
						View.generateAlert("CPF ou senha incorretos");
					}
				}
			}
		} catch (Exception e) {
			View.generateAlert(e.getMessage());
		}
	}

	public void switchToMedico() throws IOException {
		App.setRoot("Agenda");
	}

	public void switchToRecepcionista() throws IOException {
		App.setRoot("Calendario");
	}
}
