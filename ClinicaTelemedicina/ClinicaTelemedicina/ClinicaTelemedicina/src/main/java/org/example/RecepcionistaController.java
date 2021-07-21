package org.example;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import org.example.model.Recepcionista;
import org.example.utils.FieldsValidator;
import org.example.utils.JsonUtils;

public class RecepcionistaController {

	FieldsValidator validator = new FieldsValidator();
	@FXML
	private TextField name;
	@FXML
	private TextField CPF;
	@FXML
	private TextField email;
	@FXML
	private TextField endereco;
	@FXML
	private TextField telefone;
	@FXML
	private TextField findCPF;

	private EmailController emailController = new EmailController();

	boolean staSearch = false;

	public RecepcionistaController() {

	}

	public void saveRecepcionista() {

		String userCpf = "";
		String userEmail = "";
		String userName = "";
		String userEndereco = "";
		long userTelefone = 0;

		try {
			Map<String, Object> data = JsonUtils.readValues("/Jsons/Recepcionistas/Recepcionista.json");
			if (validator.isValidName(name.getText())) {
				userName = name.getText();
			}
			if (validator.isCpf(CPF.getText())) {
				userCpf = CPF.getText();
			}
			if (validator.isValidEmail(email.getText())) {
				userEmail = email.getText();
			}
			if (validator.isEnderecoValid(endereco.getText())) {
				userEndereco = endereco.getText();
			}
			if (validator.isTelefoneValid(telefone.getText())) {
				userTelefone = new Long(telefone.getText());
			}

			Entry<String, Object> mapRecepcionista = compareData(userCpf, data);
			if (mapRecepcionista != null) {
				if (this.generateConfirmationDialog("Deseja alterar o cadastro?")) {
					Map<String, Object> upMap = (Map<String, Object>) mapRecepcionista.getValue();
					Recepcionista recepcionista = new Recepcionista(userName, upMap.get("cpf").toString(), userTelefone,
							userEmail, userEndereco, upMap.get("senha").toString());
					this.persistRecepcionista(recepcionista, data, mapRecepcionista.getKey(), Actions.Update);
					this.generateAlert("Cadastro atualizado com sucesso!");
					this.setVisibleText(recepcionista);
				}

			} else {
				String userSenha = generatePassword();
				Recepcionista recepcionista = new Recepcionista(userName, userCpf, userTelefone, userEmail,
						userEndereco, userSenha);
				this.persistRecepcionista(recepcionista, data, null, Actions.Create);
				emailController.sendRecepcionistaConfirmation(recepcionista);
				this.generateAlert("Cadastro realizado com sucesso!");
			}

		} catch (Exception e) {
			this.generateAlert(e.getMessage());
		}

	}


	public Recepcionista findByCPF() {
		String cpf = this.findCPF.getText();
		return this.find(cpf);
	}

	public Recepcionista find(String cpf){
		try {

			if (validator.isCpf(cpf)) {
				Map<String, Object> data = JsonUtils.readValues(RecepcionistaController.class.getResource("Recepcionista.json").toString().substring(6));
				Recepcionista recepcionista = null;
				for (Map.Entry<String, Object> entry : data.entrySet()) {
					Map<String, Object> values = (Map<String, Object>) entry.getValue();
					if (cpf.equals(values.get("cpf"))) {
						Long telefoneValue = new Long(values.get("telefone").toString());
						recepcionista = new Recepcionista(values.get("nome").toString(), cpf, telefoneValue,
								values.get("email").toString(), values.get("endereco").toString(),
								values.get("senha").toString());
						break;
					}
				}
				if (recepcionista != null) {
					this.setVisibleText(recepcionista);
					return recepcionista;
				} else {
					throw new Exception("CPF não encontrado!");
				}
			}
		} catch (Exception e) {
			generateAlert(e.getMessage());

		}
		return null;
	}

	private Entry<String, Object> compareData(String cpf, Map<String, Object> data) {
		if(data!=null) {
			for (Map.Entry<String, Object> entry : data.entrySet()) {
				Map<String, Object> values = (Map<String, Object>) entry.getValue();
				if (cpf.equals(values.get("cpf"))) {
					return entry;

				}
			}
		}

		return null;
	}

	public void deleleteRecepcionista() {
		try {

			if (validator.isCpf(CPF.getText())) {
				if(generateConfirmationDialog("Deseja excluir o cadastro?")) {
					Map<String, Object> data = JsonUtils.readValues(RecepcionistaController.class.getResource("Recepcionista.json").toString().substring(6));
					String userCpf = CPF.getText();
					Entry<String, Object> value = compareData(userCpf, data);
					if (value != null) {
						this.persistRecepcionista(null, data, value.getKey(), Actions.Delete);
						this.generateAlert("Cadastro excluído");
						this.resetText();
					} else {
						throw new Exception("Recepcionista não cadastrado");
					}
				}

			}
		} catch (Exception e) {
			this.generateAlert("Erro ao exluir cadastro!");
		}
	}


	private String generatePassword() {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		SecureRandom random = new SecureRandom();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 8; i++) {
			int randomIndex = random.nextInt(chars.length());
			sb.append(chars.charAt(randomIndex));
		}

		return sb.toString();
	}

	private void persistRecepcionista(Recepcionista recepcionista, Map<String, Object> data, String key, Actions action)
			throws Exception {
		Map<String, Object> maps = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> values = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();

		switch (action) {

			case Create:
				String next = "1";
				if (data != null) {
					next = new Integer(data.size() + 1).toString();
				}

				values.put("cpf", recepcionista.getCpf());
				values.put("nome", recepcionista.getNome());
				values.put("email", recepcionista.getEmail());
				values.put("endereco", recepcionista.getEndereco());
				values.put("telefone", recepcionista.getTelefone());
				values.put("senha", recepcionista.getSenha());

				try {
					if (data != null) {
						data.put(next, values);
						mapper.writeValue(new File(RecepcionistaController.class.getResource("Recepcionista.json").toString().substring(6)), data);
					} else {
						map.put(next, values);
						mapper.writeValue(new File(RecepcionistaController.class.getResource("Recepcionista.json").toString().substring(6)), map);
					}

				} catch (IOException e1) {
					throw new Exception("Erro ao realizar cadastro!");
				}
				break;
			case Update:
				values.put("cpf", recepcionista.getCpf());
				values.put("nome", recepcionista.getNome());
				values.put("email", recepcionista.getEmail());
				values.put("endereco", recepcionista.getEndereco());
				values.put("telefone", recepcionista.getTelefone());
				values.put("senha", recepcionista.getSenha());

				data.replace(key, values);
				int t = 1;
				try {
					mapper.writeValue(new File(RecepcionistaController.class.getResource("Recepcionista.json").toString().substring(6)), data);
				} catch (IOException e1) {
					throw new Exception("Erro ao atualizar cadastro!");
				}
				break;
			case Delete:

				data.remove(key);
				try {
					mapper.writeValue(new File(RecepcionistaController.class.getResource("Recepcionista.json").toString().substring(6)), data);
				} catch (IOException e1) {
					throw new Exception("Erro ao excluir cadastro!");
				}
				break;
		}

	}

	private void generateAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		alert.setContentText(message);

		alert.showAndWait();
	}

	private boolean generateConfirmationDialog(String message) {
		Alert alert = new Alert(AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
		alert.showAndWait();

		if (alert.getResult() == ButtonType.YES) {
			return true;
		}
		return false;
	}

	private void setVisibleText(Recepcionista recepcionista) {
		this.CPF.setText(recepcionista.getCpf());
		this.email.setText(recepcionista.getEmail());
		this.endereco.setText(recepcionista.getEndereco());
		this.name.setText(recepcionista.getNome());
		this.telefone.setText(Long.toString(recepcionista.getTelefone()));
	}

	private void resetText() {
		this.CPF.setText("");
		this.email.setText("");
		this.endereco.setText("");
		this.name.setText("");
		this.telefone.setText("");
	}

	public void goBack(ActionEvent actionEvent) throws IOException {
		App.setRoot("Calendario");
	}
}
