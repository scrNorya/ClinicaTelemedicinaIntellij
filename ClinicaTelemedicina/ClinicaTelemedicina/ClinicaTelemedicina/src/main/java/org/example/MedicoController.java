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
import org.example.model.Medico;
import org.example.model.Recepcionista;
import org.example.utils.FieldsValidator;
import org.example.utils.JsonUtils;

public class MedicoController {

    FieldsValidator validator = new FieldsValidator();
    @FXML
    private TextField name;
    @FXML
    private TextField CPF;
    @FXML
    private TextField CRM;
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

    public MedicoController() {

    }

    public void saveMedico() {

        String userCpf = "";
        long userCRM = 0;
        String userEmail = "";
        String userName = "";
        String userEndereco = "";
        long userTelefone = 0;

        try {
            Map<String, Object> data = JsonUtils.readValues(MedicoController.class.getResource("Medico.json").toString().substring(6));
            if (validator.isValidName(name.getText())) {
                userName = name.getText();
            }
            if (validator.isCpf(CPF.getText())) {
                userCpf = CPF.getText();
            }
            if (validator.isCRMValid(CRM.getText())) {
                userCRM = new Long(CRM.getText());
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

            Entry<String, Object> mapMedico = compareData(userCpf, data);
            if (mapMedico != null) {
                if (this.generateConfirmationDialog("Deseja alterar o cadastro?")) {
                    Map<String, Object> upMap = (Map<String, Object>) mapMedico.getValue();
                    Medico medico = new Medico(userName, upMap.get("cpf").toString(), userCRM,
                            userTelefone, userEmail, userEndereco, upMap.get("senha").toString());
                    persistMedico(medico, data, mapMedico.getKey(), Actions.Update);
                    generateAlert("Cadastro atualizado com sucesso!");
                    setVisibleText(medico);
                }

            } else {
                String userSenha = generatePassword();
                Medico medico = new Medico(userName, userCpf, userCRM, userTelefone, userEmail,
                        userEndereco, userSenha);
                persistMedico(medico, data, null, Actions.Create);
                emailController.sendMedicoConfirmation(medico);
                generateAlert("Cadastro realizado com sucesso!");
            }

        } catch (Exception e) {
            generateAlert(e.getMessage());
        }

    }


    public Medico findByCPF() {
        String cpf = this.findCPF.getText();
        Medico medico = this.find(cpf);
        if (medico != null) {
            this.setVisibleText(medico);
            return medico;
        } else {
            this.generateAlert("Cpf não encontrado!");
        }
        return null;
    }

    public Medico find(String cpf){
        try {

            if (validator.isCpf(cpf)) {
                Map<String, Object> data = JsonUtils.readValues(MedicoController.class.getResource("Medico.json").toString().substring(6));
                Medico medico = null;
                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    Map<String, Object> values = (Map<String, Object>) entry.getValue();
                    if (cpf.equals(values.get("cpf"))) {
                        Long telefoneValue = new Long(values.get("telefone").toString());
                        Long CRMValue = new Long(values.get("CRM").toString());
                        medico = new Medico(values.get("nome").toString(), cpf, CRMValue ,telefoneValue,
                                values.get("email").toString(), values.get("endereco").toString(),
                                values.get("senha").toString());
                        return medico;
                    }
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

    public void deleteMedico() {
        try {

            if (validator.isCpf(CPF.getText())) {
                if(generateConfirmationDialog("Deseja excluir o cadastro?")) {
                    Map<String, Object> data = JsonUtils.readValues(MedicoController.class.getResource("Medico.json").toString().substring(6));
                    String userCpf = CPF.getText();
                    Entry<String, Object> value = compareData(userCpf, data);
                    if (value != null) {
                        this.persistMedico(null, data, value.getKey(), Actions.Delete);
                        this.generateAlert("Cadastro excluído");
                        this.resetText();
                    } else {
                        throw new Exception("Medico não cadastrado");
                    }
                }

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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

    private void persistMedico(Medico medico, Map<String, Object> data, String key, Actions action)
            throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> values = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();

        switch (action) {

            case Create:
                String next = "1";
                if (data != null) {
                    next = new Integer(data.size() + 1).toString();
                }

                values.put("cpf", medico.getCpf());
                values.put("nome", medico.getNome());
                values.put("CRM", medico.getCRM());
                values.put("email", medico.getEmail());
                values.put("endereco", medico.getEndereco());
                values.put("telefone", medico.getTelefone());
                values.put("senha", medico.getSenha());

                try {
                    if (data != null) {
                        data.put(next, values);
                        mapper.writeValue(new File(MedicoController.class.getResource("Medico.json").toString().substring(6)), data);
                    } else {
                        map.put(next, values);
                        mapper.writeValue(new File(MedicoController.class.getResource("Medico.json").toString().substring(6)), map);
                    }

                } catch (IOException e1) {
                    throw new Exception("Erro ao realizar cadastro!");
                }
                break;
            case Update:
                values.put("cpf", medico.getCpf());
                values.put("nome", medico.getNome());
                values.put("CRM", medico.getCRM());
                values.put("email", medico.getEmail());
                values.put("endereco", medico.getEndereco());
                values.put("telefone", medico.getTelefone());
                values.put("senha", medico.getSenha());

                data.replace(key, values);
                int t = 1;
                try {
                    mapper.writeValue(new File(MedicoController.class.getResource("Medico.json").toString().substring(6)), data);
                } catch (IOException e1) {
                    throw new Exception("Erro ao atualizar cadastro!");
                }
                break;
            case Delete:

                data.remove(key);
                try {
                    mapper.writeValue(new File(MedicoController.class.getResource("Medico.json").toString().substring(6)), data);
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

    private void setVisibleText(Medico medico) {
        this.CPF.setText(medico.getCpf());
        this.CRM.setText(Long.toString(medico.getCRM()));
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


    public void goBack(ActionEvent actionEvent) throws IOException {
        App.setRoot("Calendario");
    }
}
