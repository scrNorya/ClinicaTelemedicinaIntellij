package org.example.model;

/**
 * @author Caio Noguerol
 * @version 1.0
 * @created 18-jul-2021 15:43:13
 */
public class Recepcionista extends Funcionario {

	public Recepcionista(String nome, String cpf, long telefone, String email, String endereco,
						 String senha){
		super(nome, cpf, telefone, email, endereco, senha);
	}

}//end Recepcionista