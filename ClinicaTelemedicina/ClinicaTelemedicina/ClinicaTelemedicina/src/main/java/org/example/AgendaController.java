package org.example;

import java.io.IOException;

public class AgendaController {
	public void logout() throws IOException {
		App.setRoot("Login");
	}

	public void createRegistrarDiagnostico() throws IOException {
		App.setRoot("RegistrarDiagnostico");
	}
}