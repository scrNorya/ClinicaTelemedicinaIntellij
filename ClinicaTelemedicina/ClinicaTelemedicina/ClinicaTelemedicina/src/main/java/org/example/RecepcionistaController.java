package org.example;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.utils.*;
import org.example.model.Recepcionista;

public class RecepcionistaController {

	@FXML private TextField name;
	@FXML private TextField CPF;
	@FXML private TextField email;
	@FXML private TextField endereco;
	@FXML private TextField telefone;
	@FXML private TextField findCPF;

	private EmailController emailController = new EmailController();

	public Recepcionista findByCPF() throws Exception {
		String cpf = this.findCPF.getText();
		Recepcionista recepcionista = (Recepcionista) Json.findByCPF(cpf, JsonType.Recepcionista);
		if (recepcionista != null) {
			this.setVisibleText(recepcionista);
			return recepcionista;
		} else {
			View.generateAlert("Cpf não encontrado!");
		}
		return null;
	}

	public void saveRecepcionista() {
		String userCpf = "";
		String userEmail = "";
		String userName = "";
		String userEndereco = "";
		long userTelefone = 0;

		try {
			if (Validations.isValidName(name.getText()) && Validations.isCpf(CPF.getText()) &&
					Validations.isValidEmail(email.getText()) && Validations.isEnderecoValid(endereco.getText())
					&& Validations.isTelefoneValid(telefone.getText())
			) {
				userName = name.getText();
				userCpf = CPF.getText();
				userEmail = email.getText();
				userEndereco = endereco.getText();
				userTelefone = Long.parseLong(telefone.getText());

				Map.Entry<String, Object> recepcionistaEntry = Json.findEntryByCpf(userCpf, JsonType.Recepcionista);
				Recepcionista recepcionista;
				if (recepcionistaEntry != null) {
					Map<String, Object> recepcionistaMap = (Map<String, Object>) recepcionistaEntry.getValue();
					recepcionista = new Recepcionista(userName, recepcionistaMap.get("cpf").toString(),
							userTelefone, userEmail, userEndereco, recepcionistaMap.get("senha").toString());
					if (View.generateConfirmationDialog("Deseja alterar o cadastro?")) {
						this.persistRecepcionista(recepcionista, recepcionistaEntry.getKey(), Actions.Update);
						View.generateAlert("Cadastro atualizado com sucesso!");
						this.setVisibleText(recepcionista);
					}
				} else {
					String userSenha = Security.generatePassword();
					recepcionista = new Recepcionista(userName, userCpf, userTelefone, userEmail,
							userEndereco, userSenha);
					this.persistRecepcionista(recepcionista, null, Actions.Create);
					emailController.sendConfirmation(recepcionista, JsonType.Recepcionista);
					View.generateAlert("Cadastro realizado com sucesso!");
				}
			} else {
				View.generateAlert("Verifique os dados inseridos e tente novamente");
			}
		} catch (Exception e) {
			View.generateAlert(e.getMessage());
		}
	}

	public void deleleteRecepcionista() {
		try {
			if (Validations.isCpf(CPF.getText())) {
				if(View.generateConfirmationDialog("Deseja excluir o cadastro?")) {
					String userCpf = CPF.getText();
					Map.Entry<String, Object> value = Json.findEntryByCpf(userCpf, JsonType.Recepcionista);
					if (value != null) {
						this.persistRecepcionista(null, value.getKey(), Actions.Delete);
						View.generateAlert("Cadastro excluído");
						this.resetText();
					} else {
						throw new Exception("Recepcionista não cadastrado");
					}
				}
			}
		} catch (Exception e) {
			View.generateAlert("Erro ao exluir cadastro!");
		}
	}

	private void persistRecepcionista(Recepcionista recepcionista, String key, Actions action)
			throws URISyntaxException, IOException {
		switch (action) {
			case Create:
				Json.writeValue(recepcionista);
				break;
			case Update:
				Json.updateValue(recepcionista, key);
				break;
			case Delete:
				Json.deleteValue(key, JsonType.Recepcionista);
				break;
		}
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

	public void goBack() throws IOException {
		App.setRoot("Calendario");
	}
}