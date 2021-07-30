package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * @author Caio Noguerol
 * @version 1.0
 * @created 18-jul-2021 15:43:26
 */
public class Medico extends Funcionario {

	private long crm;
	//private Agenda agenda;

	public Medico(String nome, String cpf, long userCrm, long telefone, String email, String endereco,
				  String senha){
		super(nome, cpf, telefone, email, endereco, senha);
		this.crm = userCrm;
	}

	public long getCrm() {
		return crm;
	}

	public void setCrm(long CRM) {
		this.crm = crm;
	}
}//end Medico