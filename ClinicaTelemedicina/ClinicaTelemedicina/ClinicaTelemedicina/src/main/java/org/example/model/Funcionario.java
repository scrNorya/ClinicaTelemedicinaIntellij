package org.example.model;

public abstract class Funcionario extends Pessoa {

    private String senha;

    public Funcionario(String nome, String cpf, long telefone, String email, String endereco, String senha) {
        super(nome, cpf, telefone, email, endereco);
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
