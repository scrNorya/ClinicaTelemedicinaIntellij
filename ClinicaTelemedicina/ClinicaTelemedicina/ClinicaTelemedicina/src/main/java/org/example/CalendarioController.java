package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		hora.setCellValueFactory(new PropertyValueFactory<Hora, String>("hora"));
		MONDAY.setText("É sett\next sim");

		Calendar.setItems(list);
	}

	@FXML
	public void clickItem(MouseEvent event) {
		if(event.getClickCount() == 2) {
			System.out.print(Calendar.getSelectionModel().getSelectedItem().getHora());
			System.out.print(" ");
			System.out.println((Calendar.getFocusModel().getFocusedCell().getTableColumn().getId()));
		}
	}
}