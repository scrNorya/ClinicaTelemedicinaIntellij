package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.model.Consulta;
import org.example.utils.JsonUtils;
import org.example.utils.ViewUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class ConsultaController {

    @FXML private TextField data;
    @FXML private TextField horario;
    @FXML private TextField medicoConsulta;
    @FXML private TextField pacienteConsulta;
    @FXML private TextField cid;
    @FXML private TextField diagnostico;
    @FXML private TextField sala;


    public ConsultaController(){

    }

    public Consulta findConsulta(String data, String horario, String medicoConsulta, String pacienteConsulta){
        Consulta consulta = new Consulta("", "", "", "", "","","");
        setVisibleText();
        return consulta;
    }

    public void saveConsulta() throws URISyntaxException, IOException {

        String Userdata = data.getText();
        String Userhorario = horario.getText();
        String UsermedicoConsulta = medicoConsulta.getText();
        String UserpacienteConsulta = pacienteConsulta.getText();
        String Usercid = "";
        String Userdiagnostico = "";
        String Usersala = "";

        Consulta consulta = new Consulta(Usercid, Userdata, Userdiagnostico, Usersala, UsermedicoConsulta, UserpacienteConsulta, Userhorario);

        if (validaDadosConsulta(consulta)){
            JsonUtils.writeValue(consulta);
            ViewUtils.generateAlert("Consulta agendada com sucesso!");
        }else{
            ViewUtils.generateAlert("Verifique os dados inseridos e tente novamente");
        }

    }

    public void goBack() throws IOException {
        App.setRoot("Calendario");
    }

    public void deleteConsulta() throws IOException {
        //validaDados();
        //deletar consulta
        goBack();
    }

    public boolean validaDadosConsulta(Consulta consulta){
        return true;
    }

    public void setVisibleText(){

    }





}
