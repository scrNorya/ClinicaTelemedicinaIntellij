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
		init();
	}

	public void nextWeek(ActionEvent actionEvent) {
		day = day.plusDays(7);
		init();
	}

	public class PacienteConsulta {

		private String hora;
		private String MONDAY;
		private String TUESDAY;
		private String WEDNESDAY;
		private String THURSDAY;
		private String FRIDAY;

		public PacienteConsulta(String hora) {
			this.hora = hora;
		}

		public String getHora() {
			return hora;
		}

		public void setHora(String hora) {
			this.hora = hora;
		}

		public String getMONDAY() {
			return MONDAY;
		}

		public void setMONDAY(String MONDAY) {
			this.MONDAY = MONDAY;
		}

		public String getTUESDAY() {
			return TUESDAY;
		}

		public void setTUESDAY(String TUESDAY) {
			this.TUESDAY = TUESDAY;
		}

		public String getWEDNESDAY() {
			return WEDNESDAY;
		}

		public void setWEDNESDAY(String WEDNESDAY) {
			this.WEDNESDAY = WEDNESDAY;
		}

		public String getTHURSDAY() {
			return THURSDAY;
		}

		public void setTHURSDAY(String THURSDAY) {
			this.THURSDAY = THURSDAY;
		}

		public String getFRIDAY() {
			return FRIDAY;
		}

		public void setFRIDAY(String FRIDAY) {
			this.FRIDAY = FRIDAY;
		}
	}

	@FXML private TableView<PacienteConsulta> calendar = new TableView<>();

	TableView.TableViewSelectionModel<PacienteConsulta> defaultSelectionModel = calendar.getSelectionModel();

	@FXML private TableColumn<PacienteConsulta, String> hora;
	@FXML private TableColumn<PacienteConsulta, String> MONDAY;
	@FXML private TableColumn<PacienteConsulta, String> TUESDAY;
	@FXML private TableColumn<PacienteConsulta, String> WEDNESDAY;
	@FXML private TableColumn<PacienteConsulta, String> THURSDAY;
	@FXML private TableColumn<PacienteConsulta, String> FRIDAY;

	@FXML private ComboBox<String> medicoComboBox;

	ObservableList<PacienteConsulta> list = FXCollections.observableArrayList(
			new PacienteConsulta("08:00"),
			new PacienteConsulta("09:00"),
			new PacienteConsulta("10:00"),
			new PacienteConsulta("11:00"),
			new PacienteConsulta("12:00"),
			new PacienteConsulta("13:00"),
			new PacienteConsulta("14:00"),
			new PacienteConsulta("15:00"),
			new PacienteConsulta("16:00"),
			new PacienteConsulta("17:00")
	);

	public void init(){
		calendar.setItems(list);
		defaultSelectionModel = calendar.getSelectionModel();
		calendar.setSelectionModel(null);
		medicoComboBox.setItems(medicosList);
		hora.setCellValueFactory(new PropertyValueFactory<PacienteConsulta, String>("hora"));
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