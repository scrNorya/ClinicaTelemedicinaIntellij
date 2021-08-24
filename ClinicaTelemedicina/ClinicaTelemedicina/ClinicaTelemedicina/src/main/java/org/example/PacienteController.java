package org.example;
import java.io.IOException;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.utils.*;
import org.example.model.Paciente;

public class PacienteController {

    @FXML
    private TextField name;
    @FXML
    private TextField email;
    @FXML
    private TextField endereco;
    @FXML
    private TextField telefone;
    @FXML
    private TextField findCPF;

    public void findByCPF() {
        try {
            Paciente paciente = (Paciente) JsonUtils.findByCPF(findCPF.getText(), JsonType.Paciente);
            if (paciente != null) {
                setVisibleText(paciente);
            } else {
                ViewUtils.generateAlert("Paciente não encontrado");
            }
        } catch (Exception e) {
            ViewUtils.generateAlert(e.getMessage());
        }
    }

    public void deleletePaciente() {
        try {
            if (ValidationUtils.isCpf(findCPF.getText())) {
                if (ViewUtils.generateConfirmationDialog("Deseja excluir o cadastro?")) {
                    Map.Entry<String, Object> value = JsonUtils.findEntryByCpf(findCPF.getText(), JsonType.Paciente);
                    if (value != null) {
                        JsonUtils.deleteValue(value.getKey(), JsonType.Paciente);
                        ViewUtils.generateAlert("Cadastro excluído");
                        resetText();
                    } else {
                        ViewUtils.generateAlert("Paciente não encontrado");
                    }
                }
            }
        } catch (Exception e) {
            ViewUtils.generateAlert(e.getMessage());
        }
    }

    public void savePaciente () {
        try {
            if (areFieldsValid()) {
                Paciente paciente = new Paciente(name.getText(), findCPF.getText(), Long.parseLong(telefone.getText()),
                        email.getText(), endereco.getText(), null);
                Map.Entry<String, Object> pacienteEntry = JsonUtils.findEntryByCpf(findCPF.getText(), JsonType.Paciente);
                if (pacienteEntry != null) {
                    if (ViewUtils.generateConfirmationDialog("Deseja autalizar o paciente?")) {
                        JsonUtils.updateValue(paciente, pacienteEntry.getKey());
                        ViewUtils.generateAlert("Paciente atualizado");
                    }
                } else {
                    JsonUtils.writeValue(paciente);
                    ViewUtils.generateAlert("Cadastro realizado com sucesso!");
                }
            }
        } catch (Exception e) {
            ViewUtils.generateAlert(e.getMessage());
        }
    }

    private void setVisibleText (Paciente paciente){
        this.findCPF.setText(paciente.getCpf());
        this.email.setText(paciente.getEmail());
        this.endereco.setText(paciente.getEndereco());
        this.name.setText(paciente.getNome());
        this.telefone.setText(Long.toString(paciente.getTelefone()));
    }

    private void resetText () {
        this.findCPF.setText("");
        this.email.setText("");
        this.endereco.setText("");
        this.name.setText("");
        this.telefone.setText("");
    }

    public void goBack () throws IOException {
        App.setRoot("Calendario");
    }

    public boolean areFieldsValid () throws Exception {
        return ValidationUtils.isValidName(name.getText()) && ValidationUtils.isCpf(findCPF.getText()) &&
                ValidationUtils.isValidEmail(email.getText()) && ValidationUtils.isEnderecoValid(endereco.getText())
                && ValidationUtils.isTelefoneValid(telefone.getText());
    }
}