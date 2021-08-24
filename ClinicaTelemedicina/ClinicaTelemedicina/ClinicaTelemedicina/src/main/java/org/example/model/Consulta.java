package org.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.Date;

public class Consulta {


	private String cid;
	private String diagnostico;
	private String sala;
	private String medicoConsulta;
	private String pacienteConsulta;
	private String horario;
	private String data;


//	private Receita receita;

	public Consulta(String cid, String data, String diagnostico, String sala, String medicoConsulta, String pacienteConsulta, String horario){
		this.setCid(cid);
		this.setData(data);
		this.setDiagnostico(diagnostico);
		this.setSala(sala);
		this.setHorario(horario);
		this.setMedicoConsulta(medicoConsulta);
		this.setPacienteConsulta(pacienteConsulta);
	}



	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	public String getSala() {
		return sala;
	}

	public void setSala(String sala) {
		this.sala = sala;
	}

	public String getMedicoConsulta() {
		return medicoConsulta;
	}

	public void setMedicoConsulta(String medicoConsulta) {
		this.medicoConsulta = medicoConsulta;
	}

	public String getPacienteConsulta() {
		return pacienteConsulta;
	}

	public void setPacienteConsulta(String pacienteConsulta) {
		this.pacienteConsulta = pacienteConsulta;
	}
	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}
}