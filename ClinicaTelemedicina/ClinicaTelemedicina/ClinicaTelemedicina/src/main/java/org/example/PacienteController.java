package org.example;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.model.Consulta;
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
        String cpf = this.findCPF.getText();
        try {
            Paciente paciente = (Paciente) JsonUtils.findByCPF(cpf, JsonType.Paciente);
            this.setVisibleText(paciente);
        } catch (Exception e) {
            ViewUtils.generateAlert(e.getMessage());
        }
    }

    public Consulta[] getConsultasByPacienteCPF(String cpf){
        return null;
    }

    public void savePaciente() {
        String userCpf = "";
        String userEmail = "";
        String userName = "";
        String userEndereco = "";
        long userTelefone = 0;

        try {
            if (ValidationUtils.isValidName(name.getText()) && ValidationUtils.isCpf(CPF.getText()) &&
                    ValidationUtils.isValidEmail(email.getText()) && ValidationUtils.isEnderecoValid(endereco.getText())
                    && ValidationUtils.isTelefoneValid(telefone.getText())
            ) {
                userName = name.getText();
                userCpf = CPF.getText();
                userEmail = email.getText();
                userEndereco = endereco.getText();
                userTelefone = Long.parseLong(telefone.getText());

                Map.Entry<String, Object> pacienteEntry = JsonUtils.findEntryByCpf(userCpf, JsonType.Paciente);
                Paciente paciente;
                if (pacienteEntry != null) {
                    Map<String, Object> pacienteMap = (Map<String, Object>) pacienteEntry.getValue();
                    paciente = new Paciente(userName, pacienteMap.get("cpf").toString(),
                            userTelefone, userEmail, userEndereco, null);
                    if (ViewUtils.generateConfirmationDialog("Deseja alterar o cadastro?")) {
                        this.persistPaciente(paciente, pacienteEntry.getKey(), Actions.Update);
                        ViewUtils.generateAlert("Cadastro atualizado");
                        this.setVisibleText(paciente);
                    }
                } else {
                    paciente = new Paciente(userName, userCpf, userTelefone, userEmail,
                            userEndereco, null);
                    this.persistPaciente(paciente, null, Actions.Create);
                    ViewUtils.generateAlert("Cadastro realizado");
                }
            } else {
                ViewUtils.generateAlert("Verifique os dados inseridos e tente novamente");
            }
        } catch (Exception e) {
            ViewUtils.generateAlert(e.getMessage());
        }
    }

    public void deleletePaciente() {
        try {
            if (ValidationUtils.isCpf(CPF.getText())) {
                if(ViewUtils.generateConfirmationDialog("Deseja excluir o cadastro?")) {
                    String userCpf = CPF.getText();
                    Map.Entry<String, Object> value = JsonUtils.findEntryByCpf(userCpf, JsonType.Paciente);
                    if (value != null) {
                        this.persistPaciente(null, value.getKey(), Actions.Delete);
                        ViewUtils.generateAlert("Cadastro excluído");
                        this.resetText();
                    } else {
                        throw new Exception("Paciente não cadastrado");
                    }
                }
            }
        } catch (Exception e) {
            ViewUtils.generateAlert("Erro ao exluir cadastro!");
        }
    }

    private void persistPaciente(Paciente paciente, String key, Actions action)
            throws URISyntaxException, IOException {
        switch (action) {
            case Create:
                JsonUtils.writeValue(paciente);
                break;
            case Update:
                JsonUtils.updateValue(paciente, key);
                break;
            case Delete:
                JsonUtils.deleteValue(key, JsonType.Paciente);
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