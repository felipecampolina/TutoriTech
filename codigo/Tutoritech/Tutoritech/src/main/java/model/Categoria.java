package model;

public class Categoria {
	private int id;
	private String nome;
	
	public Categoria() {
		id = -1;
		nome = "";
	}

	public Categoria(int id, String nome) {
		setId(id);
		setNome(nome);
	}		
	
	public int getID() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


	/**
	 * Método sobreposto da classe Object. É executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	@Override
	public String toString() {
		return "Categoria: " + nome;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getID() == ((Categoria) obj).getID());
	}	
}