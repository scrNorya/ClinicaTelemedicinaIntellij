package org.example;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.utils.*;
import org.example.model.Paciente;

public class PacienteController {

    @FXML private TextField name;
    @FXML private TextField CPF;
    @FXML private TextField email;
    @FXML private TextField endereco;
    @FXML private TextField telefone;
    @FXML private TextField findCPF;

    public void findByCPF() {
        try {
            Paciente paciente = (Paciente) Json.findByCPF(findCPF.getText(), JsonType.Paciente);
            if (paciente != null) {
                this.setVisibleText(paciente);
            } else {
                View.generateAlert("Paciente não encontrado");
            }
        } catch (Exception e) {
            View.generateAlert(e.getMessage());
        }
    }

    public void deleletePaciente() {
        try {
            if (Validations.isCpf(CPF.getText())) {
                if(View.generateConfirmationDialog("Deseja excluir o cadastro?")) {
                    String userCpf = CPF.getText();
                    Map.Entry<String, Object> value = Json.findEntryByCpf(userCpf, JsonType.Paciente);
                    if (value != null) {
                        Json.deleteValue(value.getKey(), JsonType.Paciente);
                        View.generateAlert("Cadastro excluído");
                        this.resetText();
                    } else {
                        throw new Exception("Paciente não cadastrado");
                    }
                }
            }
        } catch (Exception e) {
            View.generateAlert("Erro ao exluir cadastro!");
        }
    }

    public void savePaciente() {
        try {
            if (areFieldsValid()) {
                Paciente paciente = new Paciente(name.getText(), CPF.getText(), Long.parseLong(telefone.getText()),
                        email.getText(), endereco.getText());
                Map.Entry<String, Object> pacienteEntry = Json.findEntryByCpf(CPF.getText(), JsonType.Paciente);
                if (pacienteEntry != null) {
                    if (View.generateConfirmationDialog("Deseja alterar o cadastro?")) {
                        Json.updateValue(paciente, pacienteEntry.getKey());
                        View.generateAlert("Cadastro atualizado com sucesso!");
                    }
                } else {
                    Json.writeValue(paciente);
                    View.generateAlert("Cadastro realizado com sucesso!");
                }
            }
        } catch (Exception e) {
            View.generateAlert(e.getMessage());
        }
    }

    private void setVisibleText(Paciente paciente) {
        this.CPF.setText(paciente.getCpf());
        this.email.setText(paciente.getEmail());
        this.endereco.setText(paciente.getEndereco());
        this.name.setText(paciente.getNome());
        this.telefone.setText(Long.toString(paciente.getTelefone()));
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

    public boolean areFieldsValid() throws Exception {
        return Validations.isValidName(name.getText()) && Validations.isCpf(CPF.getText()) &&
                Validations.isValidEmail(email.getText()) && Validations.isEnderecoValid(endereco.getText())
                && Validations.isTelefoneValid(telefone.getText());
    }
}