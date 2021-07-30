package org.example;

import java.io.IOException;

public class CalendarioController {

	public void logout() {
		try {
			App.setRoot("login");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void createRecepcionista() throws IOException {
		App.setRoot("CadastroRecepcionista");
	}

	public void createMedico() throws IOException {
		App.setRoot("CadastroMedico");
	}
}