package org.example;

import java.net.URISyntaxException;
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
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.example.model.Consulta;
import org.example.model.Paciente;
import org.example.utils.JsonUtils;
import org.example.utils.JsonType;
import org.example.utils.ViewUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
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

	public  void createConsulta() throws IOException {
		App.setRoot("AgendarConsulta");
	}
	public void previousWeek(ActionEvent actionEvent) throws URISyntaxException, IOException {
		day = day.minusDays(7);
		setColumns();
		if(medicoComboBox.getValue() != null) {
			populateCalendario();
		}
	}

	public void nextWeek(ActionEvent actionEvent) throws URISyntaxException, IOException {
		day = day.plusDays(7);
		setColumns();
		if(medicoComboBox.getValue() != null) {
			populateCalendario();
		}
	}

	public class PacienteConsulta {

		private String hora;
		private String MONDAY = "";
		private String TUESDAY = "";
		private String WEDNESDAY = "";
		private String THURSDAY = "";
		private String FRIDAY = "";

		public PacienteConsulta(String hora) {
			this.hora = hora;
		}

		public String getHora() {
			return hora;
		}

		public void setHora(String hora) {
			this.hora = hora;
		}

		public String getMonday() {
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

	@FXML private ComboBox<MedicoCombo> medicoComboBox;

	ObservableList<PacienteConsulta> list = FXCollections.observableArrayList(
			new PacienteConsulta("08"),
			new PacienteConsulta("09"),
			new PacienteConsulta("10"),
			new PacienteConsulta("11"),
			new PacienteConsulta("12"),
			new PacienteConsulta("13"),
			new PacienteConsulta("14"),
			new PacienteConsulta("15"),
			new PacienteConsulta("16"),
			new PacienteConsulta("17")
	);

	public void init() throws URISyntaxException, IOException {
		calendar.setItems(list);
		defaultSelectionModel = calendar.getSelectionModel();
		calendar.setSelectionModel(null);
		medicoComboBox.setItems(getMedicosList());
		hora.setCellValueFactory(new PropertyValueFactory<PacienteConsulta, String>("hora"));
		MONDAY.setCellValueFactory(new PropertyValueFactory<PacienteConsulta, String>("MONDAY"));
		TUESDAY.setCellValueFactory(new PropertyValueFactory<PacienteConsulta, String>("TUESDAY"));
		WEDNESDAY.setCellValueFactory(new PropertyValueFactory<PacienteConsulta, String>("WEDNESDAY"));
		THURSDAY.setCellValueFactory(new PropertyValueFactory<PacienteConsulta, String>("THURSDAY"));
		FRIDAY.setCellValueFactory(new PropertyValueFactory<PacienteConsulta, String>("FRIDAY"));

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

		setColumns();
	}

	public void setColumns(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		MONDAY.setText("SEGUNDA\n" + day.with(DayOfWeek.MONDAY).format(formatter));
		TUESDAY.setText("TERÇA\n" + day.with(DayOfWeek.TUESDAY).format(formatter));
		WEDNESDAY.setText("QUARTA\n" + day.with(DayOfWeek.WEDNESDAY).format(formatter));
		THURSDAY.setText("QUINTA\n" + day.with(DayOfWeek.THURSDAY).format(formatter));
		FRIDAY.setText("SEXTA\n" + day.with(DayOfWeek.FRIDAY).format(formatter));
	}

	public void populateCalendario() throws URISyntaxException, IOException {
		list.setAll(
			FXCollections.observableArrayList(
				new PacienteConsulta("08"),
				new PacienteConsulta("09"),
				new PacienteConsulta("10"),
				new PacienteConsulta("11"),
				new PacienteConsulta("12"),
				new PacienteConsulta("13"),
				new PacienteConsulta("14"),
				new PacienteConsulta("15"),
				new PacienteConsulta("16"),
				new PacienteConsulta("17")
			)
		);
		Map<String, Object> consultas = JsonUtils.readValues(JsonType.Consulta);
		LocalDate data;
		String hora;
		PacienteConsulta pacienteConsulta;
		for (Map.Entry<String, Object> entry : consultas.entrySet()) {
			Map<String, Object> values = (Map<String, Object>) entry.getValue();
			data = LocalDate.parse(values.get("data").toString());
			hora = values.get("horario").toString();
			if((data.isAfter(day) || data.isEqual(day)) && data.isBefore(day.plusDays(5)) &&
					medicoComboBox.getValue().getCpf().equals(values.get("medicoConsulta"))) {
				pacienteConsulta = new PacienteConsulta(hora);
				switch (data.getDayOfWeek()) {
					case MONDAY:
						pacienteConsulta.setMONDAY(values.get("pacienteConsulta").toString());
						break;
					case TUESDAY:
						pacienteConsulta.setTUESDAY(values.get("pacienteConsulta").toString());
						break;
					case WEDNESDAY:
						pacienteConsulta.setWEDNESDAY(values.get("pacienteConsulta").toString());
						break;
					case THURSDAY:
						pacienteConsulta.setTHURSDAY(values.get("pacienteConsulta").toString());
						break;
					case FRIDAY:
						pacienteConsulta.setFRIDAY(values.get("pacienteConsulta").toString());
						break;
				}
				list.set(getPacienteConsultaListIndex(hora), pacienteConsulta);
			}
		}
	}

	public int getPacienteConsultaListIndex(String hora) {
		switch (hora) {
			case "08":
				return 0;
			case "09":
				return 1;
			case "10":
				return 2;
			case "11":
				return 3;
			case "12":
				return 4;
			case "13":
				return 5;
			case "14":
				return 6;
			case "15":
				return 7;
			case "16":
				return 8;
			case "17":
				return 9;
		}
		return -1;
	}

	class MedicoCombo {
		private String cpf = "";
		private String nome = "";

		public MedicoCombo(String cpf, String nome) {
			this.cpf = cpf;
			this.nome = nome;
		}

		@Override
		public String toString() {
			return this.getNome();
		}

		public String getNome() {
			return nome;
		}

		public String getCpf() {
			return cpf;
		}
	}

	private ObservableList<MedicoCombo> getMedicosList() throws URISyntaxException, IOException {
		ObservableList<MedicoCombo> medicosList = FXCollections.observableArrayList();
		Map<String, Object> medicosJsonData = JsonUtils.readValues(JsonType.Medico);
		for(Map.Entry value: medicosJsonData.entrySet()){
			Map<String, Object> medicoObjeto = (Map<String, Object>) value.getValue();
			medicosList.add(new MedicoCombo(medicoObjeto.get("cpf").toString(), medicoObjeto.get("nome").toString()));
		}
		return medicosList;
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		try {
			init();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void onChangeMedico(ActionEvent actionEvent) throws URISyntaxException, IOException {
		if(medicoComboBox.getValue() != null) {
			calendar.setSelectionModel(defaultSelectionModel);
			calendar.getSelectionModel().setCellSelectionEnabled(true);
			populateCalendario();
		}
	}

	@FXML
	public void clickItem(MouseEvent event) throws URISyntaxException, IOException {
		String id = calendar.getFocusModel().getFocusedCell().getTableColumn().getId();
		if (medicoComboBox.getValue()!=null) {
			if (!id.equals("hora")) {

				TablePosition pos = calendar.getSelectionModel().getSelectedCells().get(0);
				int row = pos.getRow();
				PacienteConsulta item = calendar.getItems().get(row);
				TableColumn col = pos.getTableColumn();
				String cpf = (String) col.getCellObservableValue(item).getValue();
				this.setConsulta(calendar.getFocusModel().getFocusedCell().getTableColumn().getText(),calendar.getSelectionModel().getSelectedItem().getHora(), cpf);
				System.out.println(calendar.getSelectionModel().getSelectedItem().getHora());
				System.out.println(calendar.getSelectionModel().getSelectedItem().getMonday());
				System.out.println((calendar.getFocusModel().getFocusedCell().getTableColumn().getText()));
			}
		} else {
			calendar.setSelectionModel(null);
			ViewUtils.generateAlert("Selecione um medico");
		}
	}

	private void setConsulta(String data, String hora, String cpfPaciente) throws URISyntaxException, IOException {
		String dataFormated = this.dataFormatter(data);
		Map<String, Object> consultas = JsonUtils.readValues(JsonType.Consulta);
		if(!cpfPaciente.isBlank()){
			for(Map.Entry<String, Object> entry : consultas.entrySet()){
				Map<String, Object> values = (Map<String, Object>) entry.getValue();
				if(medicoComboBox.getValue().getCpf().equals(values.get("medicoConsulta")) && cpfPaciente.equals(values.get("pacienteConsulta"))){
					if(dataFormated.equals(values.get("data"))&& hora.equals(values.get("horario"))){
						Consulta consultaSelecionada = new Consulta("",dataFormated, "", values.get("sala").toString(), values.get("medicoConsulta").toString(), values.get("pacienteConsulta").toString(), values.get("horario").toString());
						ApplicationContext.getInstance().setConsultaSelecionada(consultaSelecionada);
						ApplicationContext.getInstance().getConsultaSelecionada(); // Para testar

					}
				}
			}

		}else{
			Consulta consutaMarcada = new Consulta("",dataFormated, "", "",medicoComboBox.getValue().getCpf(), "", hora);
			ApplicationContext.getInstance().setConsultaSelecionada(consutaMarcada);
			ApplicationContext.getInstance().getConsultaSelecionada();// Para testar

		}

	}

	private String dataFormatter(String data) {
		String valueData = data.split("\n")[1];
		String[] dataArray = valueData.split("/");
		return dataArray[2]+"-"+dataArray[1]+"-"+dataArray[0];


	}
}