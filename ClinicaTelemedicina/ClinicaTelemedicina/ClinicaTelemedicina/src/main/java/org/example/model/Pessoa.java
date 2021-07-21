package org.example.model;

/**
 * @author Caio Noguerol
 * @version 1.0
 * @created 18-jul-2021 15:41:53
 */
public abstract class Pessoa {

	private String nome;
	private String cpf;
	private long telefone;
	private String email;
	private String endereco;

	public Pessoa(String nome, String cpf, long telefone, String email, String endereco){
		this.nome = nome;
		this.cpf = cpf;
		this.telefone = telefone;
		this.email = email;
		this.endereco = endereco;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public long getTelefone() {
		return telefone;
	}

}//end Pessoa