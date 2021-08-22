package org.example.model;

import java.util.ArrayList;

public class Paciente extends Pessoa {

	private ArrayList<Consulta> historico;

	public Paciente(String nome, String cpf, long telefone, String email, String endereco,
					ArrayList<Consulta> historico){
		super(nome, cpf, telefone, email, endereco);
	}

	public ArrayList<Consulta> getHistorico() {
		return historico;
	}

	public void setHistorico(ArrayList<Consulta> historico) {
		this.historico = historico;
	}
}//end Paciente