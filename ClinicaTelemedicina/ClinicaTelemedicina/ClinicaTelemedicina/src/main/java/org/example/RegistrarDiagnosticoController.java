package org.example;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.example.model.Consulta;
import org.example.model.Medico;
import org.example.model.Paciente;
import org.example.utils.JsonType;
import org.example.utils.JsonUtils;
import org.example.utils.ViewUtils;

import static org.example.utils.JsonUtils.*;

public class RegistrarDiagnosticoController {

    @FXML private TextField buscaTextField;
    @FXML private TextArea diagnosticoTextArea;
    @FXML private Text pacienteText;
    @FXML private Text dataText;
    @FXML private Text horarioText;
    @FXML private TextField cidTextField;
    private Medico medicoLogado;
    private Paciente pacienteBusca;
    private Consulta consulta;
    private String idConsulta;


    public void findPaciente(){

        String cpfBusca = this.buscaTextField.getText();
        medicoLogado = ApplicationContext.getInstance().getMedicoLogado();
        try{
            pacienteBusca  = (Paciente) JsonUtils.findByCPF(cpfBusca, JsonType.Paciente);
            if(pacienteBusca!=null){
                Map<String, Object> consultas = readValues(JsonType.Consulta);
                if(consultas !=null){


                    consulta = this.findConsulta(consultas);

                    if(consulta!=null){
                        this.showPacienteInfo();
                        this.setEditableDiagnostico();
                    }else{
                        throw new Exception("Não há consulta agendada para esse paciente hoje!");
                    }
                }
            }else{
                throw new Exception("Paciente não encontrado!");
            }


        } catch (Exception e) {
            ViewUtils.generateAlert(e.getMessage());
            e.printStackTrace();
        }
    }



    private Date getDate() throws ParseException {


        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();

    }



    private Consulta findConsulta(Map<String, Object> consultas) throws Exception {
        for(Map.Entry entry: consultas.entrySet()){
            Map<String, Object> consultaObjeto = (Map<String, Object>) entry.getValue();
            Consulta consulta = new Consulta(consultaObjeto.get("cid").toString(),consultaObjeto.get("data").toString(), consultaObjeto.get("diagnostico").toString(), consultaObjeto.get("sala").toString(),consultaObjeto.get("medicoConsulta").toString(), consultaObjeto.get("pacienteConsulta").toString(),consultaObjeto.get("horario").toString());
            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();
            if(date.toString().equals(consulta.getData().toString())&&Integer.toString(time.getHour()).equals(consulta.getHorario())){
                Paciente pacienteConsulta = (Paciente) JsonUtils.findByCPF(consultaObjeto.get("pacienteConsulta").toString(), JsonType.Paciente);
                Medico medicoConsulta = (Medico) JsonUtils.findByCPF(consultaObjeto.get("medicoConsulta").toString(), JsonType.Medico);
                if(pacienteBusca.getCpf().equals(pacienteConsulta.getCpf())&&medicoLogado.getCpf().equals(medicoConsulta.getCpf())){
                    this.setIdConsulta(entry.getKey().toString());
                    return consulta;
                }
            }
        }
        return null;
    }

    public void setIdConsulta(String id) {
        this.idConsulta=id;
    }

    public void goBack() throws IOException {
        App.setRoot("Agenda");
        ApplicationContext.getInstance().getMedicoLogado();
    }

    public void saveDiagnostico() throws URISyntaxException, IOException {
        String diagnostico = this.diagnosticoTextArea.getText();
        if(!diagnostico.isBlank()){
            if(!consulta.getDiagnostico().isBlank()||!consulta.getDiagnostico().isEmpty()){
                if(ViewUtils.generateConfirmationDialog("Deseja substituir o diagnóstico salvo?")){
                    consulta.setDiagnostico(diagnostico);
                    consulta.setCid(cidTextField.getText());
                    JsonUtils.saveConsulta(idConsulta, consulta);
                    ViewUtils.generateAlert("Diagnóstico salvo com sucesso!");
                }
            }else{
                consulta.setDiagnostico(diagnostico);
                JsonUtils.saveConsulta(idConsulta,consulta);
                ViewUtils.generateAlert("Diagnóstico salvo com sucesso!");
            }
        }else{
            ViewUtils.generateAlert("Diagnóstico não pode ser em branco");
        }
    }

    private void setEditableDiagnostico(){
        this.diagnosticoTextArea.setEditable(true);
    }

    private void showPacienteInfo() {
        this.pacienteText.setText("Paciente: "+pacienteBusca.getNome());
        this.dataText.setText("Data: "+consulta.getData());
        this.horarioText.setText("Horário: "+ consulta.getHorario()+":00");
        this.diagnosticoTextArea.setText(consulta.getDiagnostico());
    }
}
