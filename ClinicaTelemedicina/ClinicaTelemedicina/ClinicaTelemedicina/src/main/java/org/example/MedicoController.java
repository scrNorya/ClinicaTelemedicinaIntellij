package org.example;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.utils.*;
import org.example.model.Medico;

public class MedicoController {

    @FXML private TextField name;
    @FXML private TextField CPF;
    @FXML private TextField CRM;
    @FXML private TextField email;
    @FXML private TextField endereco;
    @FXML private TextField telefone;
    @FXML private TextField findCPF;

    private EmailController emailController = new EmailController();

    public Medico findByCPF() throws Exception {
        String cpf = this.findCPF.getText();
        Medico medico = (Medico) JsonUtils.findByCPF(cpf, JsonType.Medico);
        if (medico != null) {
            this.setVisibleText(medico);
            return medico;
        } else {
            ViewUtils.generateAlert("Cpf não encontrado!");
        }
        return null;
    }

    public void saveMedico() {
        String userCpf = "";
        long userCRM = 0;
        String userEmail = "";
        String userName = "";
        String userEndereco = "";
        long userTelefone = 0;

        try {
            if (ValidationUtils.isValidName(name.getText()) && ValidationUtils.isCpf(CPF.getText()) &&
                    ValidationUtils.isCRMValid(CRM.getText()) && ValidationUtils.isValidEmail(email.getText()) &&
                    ValidationUtils.isEnderecoValid(endereco.getText()) && ValidationUtils.isTelefoneValid(telefone.getText())
            ) {
                userName = name.getText();
                userCpf = CPF.getText();
                userCRM = Long.parseLong(CRM.getText());
                userEmail = email.getText();
                userEndereco = endereco.getText();
                userTelefone = Long.parseLong(telefone.getText());

                Map.Entry<String, Object> MedicoEntry = JsonUtils.findEntryByCpf(userCpf, JsonType.Medico);
                Medico medico;
                if (MedicoEntry != null) {
                    Map<String, Object> medicoMap = (Map<String, Object>) MedicoEntry.getValue();
                    medico = new Medico(userName, medicoMap.get("cpf").toString(),userCRM, userTelefone,
                            userEmail, userEndereco, medicoMap.get("senha").toString());
                    if (ViewUtils.generateConfirmationDialog("Deseja alterar o cadastro?")) {
                        this.persistMedico(medico, MedicoEntry.getKey(), Actions.Update);
                        ViewUtils.generateAlert("Cadastro atualizado com sucesso!");
                        this.setVisibleText(medico);
                    }
                } else {
                    String userSenha = SecurityUtils.generatePassword();
                    medico = new Medico(userName, userCpf, userCRM, userTelefone, userEmail,
                            userEndereco, userSenha);
                    this.persistMedico(medico, null, Actions.Create);
                    emailController.sendConfirmation(medico, JsonType.Medico);
                    ViewUtils.generateAlert("Cadastro realizado com sucesso!");
                }
            } else {
                ViewUtils.generateAlert("Verifique os dados inseridos e tente novamente");
            }
        } catch (Exception e) {
            ViewUtils.generateAlert(e.getMessage());
        }
    }

    public void deleteMedico() {
        try {
            if (ValidationUtils.isCpf(CPF.getText())) {
                if(ViewUtils.generateConfirmationDialog("Deseja excluir o cadastro?")) {
                    String userCpf = CPF.getText();
                    Map.Entry<String, Object> value = JsonUtils.findEntryByCpf(userCpf, JsonType.Medico);
                    if (value != null) {
                        this.persistMedico(null, value.getKey(), Actions.Delete);
                        ViewUtils.generateAlert("Cadastro excluído");
                        this.resetText();
                    } else {
                        throw new Exception("Recepcionista não cadastrado");
                    }
                }
            }
        } catch (Exception e) {
            ViewUtils.generateAlert("Erro ao exluir cadastro!");
        }
    }

    private void persistMedico(Medico medico, String key, Actions action)
            throws URISyntaxException, IOException {
        switch (action) {
            case Create:
                JsonUtils.writeValue(medico);
                break;
            case Update:
                JsonUtils.updateValue(medico, key);
                break;
            case Delete:
                JsonUtils.deleteValue(key, JsonType.Medico);
                break;
        }
    }

    private void setVisibleText(Medico medico) {
        this.CPF.setText(medico.getCpf());
        this.CRM.setText(Long.toString(medico.getCrm()));
        this.email.setText(medico.getEmail());
        this.endereco.setText(medico.getEndereco());
        this.name.setText(medico.getNome());
        this.telefone.setText(Long.toString(medico.getTelefone()));
    }

    private void resetText() {
        this.CPF.setText("");
        this.CRM.setText("");
        this.email.setText("");
        this.endereco.setText("");
        this.name.setText("");
        this.telefone.setText("");
    }

    public void goBack() throws IOException {
        App.setRoot("Calendario");
    }
}