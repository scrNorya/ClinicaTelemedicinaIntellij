package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.model.Consulta;
import org.example.model.Medico;
import org.example.model.Paciente;
import org.example.utils.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import static org.example.utils.JsonUtils.findByCPF;
import static org.example.utils.JsonUtils.readValues;

public class ConsultaController {

    @FXML
    private TextField data;
    @FXML
    private TextField horario;
    @FXML
    private TextField medicoConsulta;
    @FXML
    private TextField pacienteConsulta;
    @FXML
    private TextField cid;
    @FXML
    private TextField diagnostico;
    @FXML
    private TextField sala;

    private EmailController emailController = new EmailController();

    public ConsultaController() {
    }

    public void saveConsulta() {

        String userData = "";
        String userHorario = "";
        String userMedicoConsulta = "";
        String userPacienteConsulta = "";
        String userCid = "";
        String userDiagnostico = "";
        String userSala = "";

        try {
            if (ValidationUtils.isCpf(medicoConsulta.getText()) && ValidationUtils.isCpf(pacienteConsulta.getText())
                    && ValidationUtils.isDataValid(data.getText()) && ValidationUtils.isHoraValid(horario.getText())
            ) {

                userData = data.getText();
                userHorario = horario.getText();
                userMedicoConsulta = medicoConsulta.getText();
                userPacienteConsulta = pacienteConsulta.getText();

                if (JsonUtils.findByCPF(userMedicoConsulta, JsonType.Medico) == null) {
                    ViewUtils.generateAlert("Médico inválido");
                } else if (JsonUtils.findByCPF(userPacienteConsulta, JsonType.Paciente) == null) {
                    ViewUtils.generateAlert("Paciente inválido");
                } else {
                    Map<String, Object> consultas = readValues(JsonType.Consulta);

                    for (Map.Entry entry : consultas.entrySet()) {
                        Map<String, Object> consultaObjeto = (Map<String, Object>) entry.getValue();
                        Consulta pesquisaConsulta = new Consulta(consultaObjeto.get("cid").toString(), consultaObjeto.get("data").toString(),
                                consultaObjeto.get("diagnostico").toString(), consultaObjeto.get("sala").toString(),
                                consultaObjeto.get("medicoConsulta").toString(), consultaObjeto.get("pacienteConsulta").toString(),
                                consultaObjeto.get("horario").toString());

                        if (userData.equals(pesquisaConsulta.getData()) && userHorario.equals(pesquisaConsulta.getHorario()) &&
                                userMedicoConsulta.equals(pesquisaConsulta.getMedicoConsulta())) {
                            ViewUtils.generateAlert("O Médico já possui uma consulta marcada nesse horario");
                            return;


                        } else if (userData.equals(pesquisaConsulta.getData()) && userHorario.equals(pesquisaConsulta.getHorario()) &&
                                userPacienteConsulta.equals(pesquisaConsulta.getPacienteConsulta())) {
                            ViewUtils.generateAlert("O paciente já possui uma consulta marcada nesse horario");
                            return;

                        }
                    }
                    Consulta consulta = new Consulta(userCid, userData, userDiagnostico, userSala, userMedicoConsulta,
                            userPacienteConsulta, userHorario);
                    consulta.setSala(alocaSala(consulta));
                    JsonUtils.writeValue(consulta);
                    emailController.sendConsultaConfirmation(consulta, (Paciente) JsonUtils.findByCPF(userPacienteConsulta, JsonType.Paciente), JsonType.Paciente);
                    ViewUtils.generateAlert("Consulta agendada com sucesso!");
                }
            } else {
                ViewUtils.generateAlert("Verifique os dados inseridos e tente novamente");
            }
        } catch (Exception e) {
            ViewUtils.generateAlert(e.getMessage());
        }
    }

    public String alocaSala(Consulta consulta) throws IOException, URISyntaxException {

        Map<String, Object> consultas = readValues(JsonType.Consulta);
        int salasAlocadas = 1;

        for (Map.Entry entry : consultas.entrySet()) {
            Map<String, Object> consultaObjeto = (Map<String, Object>) entry.getValue();
            Consulta pesquisaConsulta = new Consulta(consultaObjeto.get("cid").toString(), consultaObjeto.get("data").toString(),
                    consultaObjeto.get("diagnostico").toString(), consultaObjeto.get("sala").toString(),
                    consultaObjeto.get("medicoConsulta").toString(), consultaObjeto.get("pacienteConsulta").toString(),
                    consultaObjeto.get("horario").toString());

            if (consulta.getData().equals(pesquisaConsulta.getData()) && consulta.getHorario().equals(pesquisaConsulta.getHorario())) {
                salasAlocadas++;
            }
        }
        String sala = String.valueOf(salasAlocadas);
        return sala;
    }

    public void goBack() throws IOException {
        App.setRoot("Calendario");
    }

    public void deleteConsulta() throws IOException {


    }

    public void setVisibleText() {
    }
}