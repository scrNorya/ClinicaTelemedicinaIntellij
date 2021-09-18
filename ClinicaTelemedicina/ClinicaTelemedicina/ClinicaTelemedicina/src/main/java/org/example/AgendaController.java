package org.example;

import javafx.event.ActionEvent;

import java.io.IOException;

public class AgendaController {
	public void logout() throws IOException {
		App.setRoot("Login");
	}

	public void createRegistrarDiagnostico() throws IOException {
		App.setRoot("RegistrarDiagnostico");
	}

	public void viewHistoricoMedico(ActionEvent actionEvent) throws IOException {
		App.setRoot("HistoricoMedico");
	}
}