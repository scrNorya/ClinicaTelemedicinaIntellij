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

    public Paciente findByCPF() throws Exception {
        String cpf = this.findCPF.getText();
        Paciente paciente = (Paciente) Json.findByCPF(cpf, JsonType.Paciente);
        if (paciente != null) {
            this.setVisibleText(paciente);
            return paciente;
        } else {
            View.generateAlert("Cpf não encontrado!");
        }
        return null;
    }

    public void savePaciente() {
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

                Map.Entry<String, Object> pacienteEntry = Json.findEntryByCpf(userCpf, JsonType.Paciente);
                Paciente paciente;
                if (pacienteEntry != null) {
                    Map<String, Object> pacienteMap = (Map<String, Object>) pacienteEntry.getValue();
                    paciente = new Paciente(userName, pacienteMap.get("cpf").toString(),
                            userTelefone, userEmail, userEndereco);
                    if (View.generateConfirmationDialog("Deseja alterar o cadastro?")) {
                        this.persistPaciente(paciente, pacienteEntry.getKey(), Actions.Update);
                        View.generateAlert("Cadastro atualizado com sucesso!");
                        this.setVisibleText(paciente);
                    }
                } else {
                    paciente = new Paciente(userName, userCpf, userTelefone, userEmail,
                            userEndereco);
                    this.persistPaciente(paciente, null, Actions.Create);
                    View.generateAlert("Cadastro realizado com sucesso!");
                }
            } else {
                View.generateAlert("Verifique os dados inseridos e tente novamente");
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
                        this.persistPaciente(null, value.getKey(), Actions.Delete);
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

    private void persistPaciente(Paciente paciente, String key, Actions action)
            throws URISyntaxException, IOException {
        switch (action) {
            case Create:
                Json.writeValue(paciente);
                break;
            case Update:
                Json.updateValue(paciente, key);
                break;
            case Delete:
                Json.deleteValue(key, JsonType.Paciente);
                break;
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
}