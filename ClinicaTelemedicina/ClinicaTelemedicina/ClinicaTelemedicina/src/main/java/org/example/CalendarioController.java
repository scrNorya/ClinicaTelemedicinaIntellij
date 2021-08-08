package org.example;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

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
		System.out.println(day);
		init();
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

	@FXML private TableView<Hora> calendar;

	@FXML private TableColumn<Hora, String> hora;

	@FXML private TableColumn<Hora, String> MONDAY;

	@FXML private TableColumn<Hora, String> TUESDAY;

	@FXML private TableColumn<Hora, String> WEDNESDAY;

	@FXML private TableColumn<Hora, String> THURSDAY;

	@FXML private TableColumn<Hora, String> FRIDAY;

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
		hora.setCellValueFactory(new PropertyValueFactory<Hora, String>("hora"));
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

		calendar.setItems(list);
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		init();
	}

	@FXML
	public void clickItem(MouseEvent event) {
		if(event.getClickCount() == 2) {
			System.out.print(calendar.getSelectionModel().getSelectedItem().getHora());
			System.out.print(" ");
			System.out.println((calendar.getFocusModel().getFocusedCell().getTableColumn().getId()));
		}
	}
}