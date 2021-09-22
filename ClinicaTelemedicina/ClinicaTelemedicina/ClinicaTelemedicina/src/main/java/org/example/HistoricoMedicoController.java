package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.model.Consulta;
import org.example.model.Medico;
import org.example.utils.JsonUtils;
import org.example.utils.ViewUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class HistoricoMedicoController {
    @FXML
    public Label consultas;
    @FXML
    private TextField CPF;

    Medico medicoLogado = ApplicationContext.getInstance().getMedicoLogado();

    public void goBack(ActionEvent actionEvent) throws IOException, URISyntaxException {
        App.setRoot("Agenda");
    }

    public void onSearch() {
        try {
            ArrayList<Consulta> historico =
                    JsonUtils.getConlsultasFromPacienteAndMedicoByCPF(CPF.getText(), medicoLogado.getCpf());
            String consultasText = "";
            String dataFormatada;
            String[] splittedDate;
            boolean temConsultaAgora = false;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            for (Consulta c : historico) {
                splittedDate = c.getData().split("-");
                LocalDateTime dataHora = LocalDateTime.of(
                        Integer.parseInt(splittedDate[0]),
                        Integer.parseInt(splittedDate[1]),
                        Integer.parseInt(splittedDate[2]),
                        Integer.parseInt(c.getHorario()),
                        0
                );
                if (dataHora.getHour() == LocalDateTime.now().getHour()) {
                    temConsultaAgora = true;
                }
                    dataFormatada = dataHora.format(formatter);
                    consultasText +=
                            dataFormatada + " - " + c.getHorario() + ":00h\n" +
                                    "    Sala: " + c.getSala() + "\n" +
                                    "    CID: " + (c.getCid().equals("") ? "Não informado." : c.getCid()) + "\n" +
                                    "    Diagnostico: " + c.getDiagnostico() + "\n\n";
            }
            if (temConsultaAgora) {
                consultas.setText(consultasText);
            } else {
                consultas.setText("");
                ViewUtils.generateAlert("Paciente sem consulta nesse horário " +
                        "(" + LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute() + ").\n Para visualizar " +
                        "o histórico médico é necessário que o paceinte tenha uma consulta " +
                        "marcada para esse horário com você.");

            }
        } catch (Exception e) {
            consultas.setText("");
            ViewUtils.generateAlert(e.getMessage());
        }
    }
}
