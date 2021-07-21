package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * @author Caio Noguerol
 * @version 1.0
 * @created 18-jul-2021 15:43:26
 */
public class Medico extends Pessoa {

	private long CRM;
	private String senha;
	//private Agenda agenda;

	public Medico(String nome, String cpf, long userCRM, long telefone, String email, String endereco,
				  String senha){
		super(nome, cpf, telefone, email, endereco);
		this.CRM = userCRM;
		this.senha = senha;
	}

	public long getCRM() {
		return CRM;
	}

	public void setCrm(long CRM) {
		this.CRM = CRM;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}



}//end Medico