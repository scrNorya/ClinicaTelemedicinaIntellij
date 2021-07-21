package org.example.model;

/**
 * @author Caio Noguerol
 * @version 1.0
 * @created 18-jul-2021 15:43:26
 */
public class Medico extends Pessoa {

	private long crm;
	private String senha;
	//private Agenda agenda;

	public Medico(String nome, String cpf, long telefone, String email, String endereco,
			String senha, long crm){
		super(nome, cpf, telefone, email, endereco);
		this.crm = crm;
		this.senha = senha;
	}

	public long getCrm() {
		return crm;
	}

	public void setCrm(long crm) {
		this.crm = crm;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
	
}//end Medico