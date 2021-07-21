package org.example;

import java.security.SecureRandom;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.Stage;
import org.example.model.Recepcionista;
import org.example.utils.FieldsValidator;

public class RecepcionistaController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;

	FieldsValidator validator = new FieldsValidator();
	@FXML private TextField name;
	@FXML private TextField CPF;
	@FXML private TextField email;
	@FXML private TextField endereco;	
	@FXML private TextField telefone;

	
	public void back(ActionEvent e) {
		try {
			App.setRoot("Calendario");
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	public void onChange(InputMethodEvent inputMethodEvent){
		System.out.println("teste");
		System.out.println(inputMethodEvent.getComposed().toString());
		if (!inputMethodEvent.getCommitted().matches("\\d*")) {
			name.setText(inputMethodEvent.getCommitted().replaceAll("[^\\d]", ""));
		}
	}


	public RecepcionistaController() {
		
	}

	public void createRecepcionista() {
		String userCpf = "";
		String userEmail = "";
		String userName= "";
		String userEndereco = "";
		long userTelefone = 0;
		
		try {
			if (validator.isValidName(name.getText())) {
				userName = name.getText();
			}
			if (validator.isCpf(CPF.getText())){
				userCpf = CPF.getText();
			}
			if(validator.isValidEmail(email.getText())){ 
				userEmail = email.getText();
			}
			if (validator.isEnderecoValid(endereco.getText())) {
				userEndereco = endereco.getText();
			}
			if (validator.isTelefoneValid(telefone.getText())) {
				userTelefone = new Long (telefone.getText());
			}
			
			String userSenha = generatePassword();
			
			Recepcionista recepcionista = new Recepcionista(userName, userCpf, userTelefone,
					userEmail, userEndereco, userSenha);
				persistRecepcionista(recepcionista);
		}catch(Exception e) {
			generateAlert(e.getMessage());
		}
		
		

	}

	public Recepcionista findByCPF(String cpf) {
		try {
			if (validator.isCpf(cpf)) {
				Recepcionista recepcionista = readJsonRecepcionista(cpf);
				return recepcionista;
			}
		} catch (Exception e) {
			generateAlert(e.getMessage());
		}

		return null;

	}

	public void updateRecepcionista(String cpf, String email, String endereco, String nome, long telefone) {
		Recepcionista recepcionista = findByCPF(cpf);
		
//		try {
//			if (validator.isCpf(CPF.getText())){
//				userCpf = CPF.getText();
//			}
//			if(validator.isValidEmail(email.getText())){ 
//				userEmail = email.getText();
//			}
//			if (validator.isValidName(name.getText())) {
//				userName = name.getText();
//			}
//			if (validator.isTelefoneValid(new Long (telefone.getText()))) {
//				userTelefone = new Long (telefone.getText());
//			}
//			if (validator.isEnderecoValid(endereco.getText())) {
//				userEndereco = endereco.getText();
//			}
//		} catch (Exception e) {
//			generateAlert(e.getMessage());
//		}
		
	}

	public void deleleteRecepcionista() {

	}

	private Recepcionista readJsonRecepcionista(String cpf) {
		return null;
		// TODO Auto-generated method stub

	}

	public void update(long cpf, String email, String endereco, String nome, long telefone) {

	}

	private String generatePassword() {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++)
        {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }
 
        return sb.toString();
	}

	private void persistRecepcionista(Recepcionista recepcionista) {
		// TODO Auto-generated method stub

	}
	
	private void generateAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		alert.setContentText(message);

		alert.showAndWait();
	}
}
