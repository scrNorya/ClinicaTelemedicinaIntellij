package org.example;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

	LocalDate day = LocalDate.now();

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

	public void previousWeek(ActionEvent actionEvent) {
		day = day.minusDays(7);
		setColumns();
	}

	public void nextWeek(ActionEvent actionEvent) {
		day = day.plusDays(7);
		setColumns();
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
	@FXML private TableView<Hora> calendar = new TableView<>();

	TableView.TableViewSelectionModel<Hora> defaultSelectionModel = calendar.getSelectionModel();

	@FXML private TableColumn<Hora, String> hora;

	@FXML private TableColumn<Hora, String> MONDAY;

	@FXML private TableColumn<Hora, String> TUESDAY;

	@FXML private TableColumn<Hora, String> WEDNESDAY;

	@FXML private TableColumn<Hora, String> THURSDAY;

	@FXML private TableColumn<Hora, String> FRIDAY;

	@FXML private ComboBox<String> medicoComboBox;

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

	public void init(){
		calendar.setItems(list);
		defaultSelectionModel = calendar.getSelectionModel();
		calendar.setSelectionModel(null);
		medicoComboBox.setItems(medicosList);
		hora.setCellValueFactory(new PropertyValueFactory<Hora, String>("hora"));

		setColumns();

		calendar.getColumns().addListener(new ListChangeListener() {
			@Override
			public void onChanged(Change change) {
				change.next();
				if (change.wasReplaced()) {
					calendar.getColumns().clear();
					calendar.getColumns().addAll(hora, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY);
				}
			}
		});
	}

	public void setColumns(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		MONDAY.setText("SEGUNDA\n" +
				day.with(DayOfWeek.MONDAY).format(formatter));
		TUESDAY.setText("TERÇA\n" +
				day.with(DayOfWeek.TUESDAY).format(formatter));
		WEDNESDAY.setText("QUARTA\n" +
				day.with(DayOfWeek.WEDNESDAY).format(formatter));
		THURSDAY.setText("QUINTA\n" +
				day.with(DayOfWeek.THURSDAY).format(formatter));
		FRIDAY.setText("SEXTA\n" +
				day.with(DayOfWeek.FRIDAY).format(formatter));
	}


	// TODO <<<<<<<<<<<PEGAR DADOS DO JSON PARA A MEDICOSLIST!!!!!!>>>>>>>>>>>>

	ObservableList<String> medicosList = FXCollections.observableArrayList("Teste", "teste 1", "teste 2", "teste3");
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		init();
	}

	public void onChangeMedico(ActionEvent actionEvent) {
		if(medicoComboBox.getValue() != null) {
			calendar.setSelectionModel(defaultSelectionModel);
			calendar.getSelectionModel().setCellSelectionEnabled(true);
		}
	}

	@FXML
	public void clickItem(MouseEvent event) {
		String id = calendar.getFocusModel().getFocusedCell().getTableColumn().getId();
		if (medicoComboBox.getValue()!=null) {
			if (!id.equals("hora")) {
				System.out.println(calendar.getSelectionModel().getSelectedItem().getHora());
				System.out.println((calendar.getFocusModel().getFocusedCell().getTableColumn().getId()));
			}
		} else {
			calendar.setSelectionModel(null);
			View.generateAlert("Selecione um medico");
		}
	}
}