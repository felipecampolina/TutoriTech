package model;

public class Tutorial {
	private int id;
	private String titulo;
	private String texto;
	private String url;
	private int catid;
	
	
	public Tutorial() {
		id = -1;
		titulo = "";
		texto = "";
		url = "";
		catid = -1;
	}

	public Tutorial(int id, String titulo, String texto, String url, int catid) {
		setId(id);
		setTitulo(titulo);
		setTexto(texto);
		setUrl(url);
		setCatid(catid);
	}		
	
	public int getID() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getCatid() {
		return catid;
	}

	public void setCatid(int catid) {
		this.catid = catid;
	}

	/**
	 * Método sobreposto da classe Object. É executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	@Override
	
	public String toString() {
		return "Titulo: " + titulo + "   Texto: " + texto + "   URL: " + url + "   Categoria: " + catid;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getID() == ((Tutorial) obj).getID());
	}	
}