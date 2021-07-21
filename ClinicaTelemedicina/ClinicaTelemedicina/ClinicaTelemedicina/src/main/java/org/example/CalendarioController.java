package org.example;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CalendarioController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public void logout(ActionEvent e) {
		try {
			App.setRoot("login");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void createRecepcionista(ActionEvent e) throws IOException{
			App.setRoot("CadastroRecepcionista");
	}
}