package model;

public class Usuario {
	private String email;
	private String nome;
	private String telefone;
	private String senha;
	private String cep;
	private String cidade;
	private String rua;
	private int numero;
	private String complemento;
	
	public Usuario() {
		email = "";
		nome = "";
		telefone = "";
		senha = "";
		cep = "";
		cidade = "";
		rua = "";
		numero = 0;
		complemento = "";
		
	}

	public Usuario(String email, String nome, String telefone, String senha, String cep, String cidade, String rua, int numero, String complemento) {
		setEmail(email);
		setNome(nome);
		setTelefone(telefone);
		setSenha(senha);
		setCep(cep);
		setCidade(cidade);
		setRua(rua);
		setNumero(numero);
		setComplemento(complemento);
	}		
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getCep() {
		return cep;
	}
	
	public void setCep(String cep) {
		this.cep = cep;
	}
	
	public String getCidade() {
		return cidade;
	}
	
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	public String getRua() {
		return rua;
	}
	
	public void setRua(String rua) {
		this.rua = rua;
	}
	
	public int getNumero() {
		return numero;
	}
	
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	public String getComplemento() {
		return complemento;
	}
	
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	/**
	 * Método sobreposto da classe Object. É executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	@Override
	public String toString() {
		return "Email do Usuario: " + email + " Nome do usuario: " + nome + " Telefone: " + telefone +
				" Senha: " + senha + " cep: " + cep + " Cidade: " + cidade + " Rua: " + rua + " Numero: "
				+ numero + " Complemento: " + complemento;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getEmail() == ((Usuario) obj).getEmail());
	}	
}