package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.example.utils.View;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CalendarioController implements Initializable {

    public void logout() {
        try {
            App.setRoot("Login");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void createRecepcionista() throws IOException {
        App.setRoot("CadastroRecepcionista");
    }

    public void createMedico() throws IOException {
        App.setRoot("CadastroMedico");
    }

    public void createPaciente() throws IOException {
        App.setRoot("CadastroPaciente");
    }

    public class Hora {
        private final String hora;

        public Hora(String hora) {
            this.hora = hora;
        }

        public String getHora() {
            return hora;
        }
    }

    @FXML
    private TableView<Hora> Calendar;
    @FXML
    private TableColumn<Hora, String> hora;

    @FXML
    private TableColumn<Hora, String> MONDAY;

    @FXML
    private TableColumn<Hora, String> TUESDAY;

    @FXML
    private TableColumn<Hora, String> WEDNESDAY;

    @FXML
    private TableColumn<Hora, String> THURSDAY;

    @FXML
    private TableColumn<Hora, String> FRIDAY;

    @FXML
    private ComboBox<String> medicoComboBox;


    ObservableList<Hora> list = FXCollections.observableArrayList(
            new Hora("08:00"),
            new Hora("09:00"),
            new Hora("10:00"),
            new Hora("11:00"),
            new Hora("12:00"),
            new Hora("13:00"),
            new Hora("14:00"),
            new Hora("15:00"),
            new Hora("16:00"),
            new Hora("17:00")
    );
    // <<<<<<<<<<<PEGAR DADOS DO JSON PARA A MEDICOSLIST!!!!!!>>>>>>>>>>>>
    ObservableList<String> medicosList = FXCollections.observableArrayList("Teste", "teste 1", "teste 2", "teste3");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hora.setCellValueFactory(new PropertyValueFactory<Hora, String>("hora"));
        medicoComboBox.setItems(medicosList);
        Calendar.setItems(list);
        Calendar.getSelectionModel().setCellSelectionEnabled(true);
        Calendar.getColumns().addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change change) {
                change.next();
                if (change.wasReplaced()) {
                    Calendar.getColumns().clear();
                    Calendar.getColumns().addAll(hora, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY);
                }
            }
        });
    }

    @FXML
    public void clickItem(MouseEvent event) {
        String id = Calendar.getFocusModel().getFocusedCell().getTableColumn().getId();
        if (medicoComboBox.getValue()!=null) {
            if (event.getClickCount() == 2 && !id.equals("hora")) {
                System.out.print(Calendar.getSelectionModel().getSelectedItem().getHora());
                System.out.print(" ");
                System.out.println((Calendar.getFocusModel().getFocusedCell().getTableColumn().getId()));
            }
        }else{
            View.generateAlert("Selecione um médico!");
        }
    }
}