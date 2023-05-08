package model;

public class Admin {
	private String nome;
	private String senha;
	
	public Admin() {
		nome = "";
		senha = "";
	}

	public Admin(String nome, String senha) {
		setNome(nome);
		setSenha(senha);
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}


	/**
	 * Método sobreposto da classe Object. É executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	@Override
	public String toString() {
		return "Categoria: " + nome + "   Senha: " + senha;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getNome().equals(((Admin) obj).getNome()));
	}	
}