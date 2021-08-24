package org.example;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.model.Medico;
import org.example.model.Recepcionista;
import org.example.utils.JsonUtils;
import org.example.utils.JsonType;
import org.example.utils.ValidationUtils;
import org.example.utils.ViewUtils;;


public class LoginController {

	@FXML private TextField username;

	@FXML private PasswordField password;

	private Recepcionista recepcionista = null;

	private Medico medico = null;

	public void login() {

		try {
			if(ValidationUtils.isCpf(username.getText()) && ValidationUtils.isValidPassword(password.getText())) {
				recepcionista = (Recepcionista) JsonUtils.findByCPF(username.getText(), JsonType.Recepcionista);
				if(recepcionista != null  && recepcionista.getSenha().equals(password.getText())) {
					switchToRecepcionista();
				} else {
					medico = (Medico) JsonUtils.findByCPF(username.getText(), JsonType.Medico);
					if(medico != null && medico.getSenha().equals(password.getText())) {
						ApplicationContext.getInstance().setMedicoLogado(medico);
						switchToMedico();
					} else {
						ViewUtils.generateAlert("CPF ou senha incorretos");
					}
				}
			}
		} catch (Exception e) {
			ViewUtils.generateAlert(e.getMessage());
		}
	}

	public void switchToMedico() throws IOException {
		App.setRoot("Agenda");
	}

	public void switchToRecepcionista() throws IOException {
		App.setRoot("Calendario");
	}
}
