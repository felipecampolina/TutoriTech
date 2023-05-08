package dao;

import model.Categoria;
import model.Tutorial;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class TutorialDAO extends DAO {	
	public TutorialDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	
	public boolean insert(Tutorial tutorial) {
		boolean status = false;
		try {
			String sql = "INSERT INTO tutorial (titulo, texto, url, catid) "
		               + "VALUES ('" + tutorial.getTitulo() + "', '"
		               + tutorial.getTexto() + "', '" + tutorial.getUrl() + "', " + tutorial.getCatid() + ");";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	public Tutorial get(int id) {
		Tutorial tutorial = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM tutorial WHERE id="+id;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 tutorial = new Tutorial(rs.getInt("id"), rs.getString("titulo"), rs.getString("texto"), rs.getString("url"), rs.getInt("catid"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return tutorial;
	}
	
	public Categoria getCat(int catid) {
		Categoria categoria = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM categoria WHERE id=" + catid;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	categoria = new Categoria(rs.getInt("id"), rs.getString("nome"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return categoria;
	}
	
	public List<Categoria> getCats() {
		List<Categoria> categorias = new ArrayList<Categoria>();
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM categoria";
			ResultSet rs = st.executeQuery(sql);
	        while(rs.next()) {	            	
	        	Categoria p = new Categoria(rs.getInt("id"), rs.getString("nome"));
	            categorias.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return categorias;
	}
	
	
	public List<Tutorial> get() {
		return get("");
	}

	
	public List<Tutorial> getOrderByID() {
		return get("id");		
	}
	
	
	public List<Tutorial> getOrderByTitulo() {
		return get("titulo");		
	}
	
	public List<Tutorial> getOrderByCatid() {
		return get("catid");		
	}
	
	private List<Tutorial> get(String orderBy) {
		List<Tutorial> tutorials = new ArrayList<Tutorial>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM tutorial" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Tutorial p = new Tutorial(rs.getInt("id"), rs.getString("titulo"), rs.getString("texto"), rs.getString("url"), rs.getInt("catid"));
	            tutorials.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return tutorials;
	}
	
	
	public boolean update(Tutorial tutorial) {
		boolean status = false;
		try {  
			String sql = "UPDATE tutorial SET titulo = '" + tutorial.getTitulo() + "', "
					   + "texto = '" + tutorial.getTexto() + "', " 
					   + "url = '" + tutorial.getUrl() + "',"
					   + "catid = "+ tutorial.getCatid() +" WHERE id = " + tutorial.getID();
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public boolean delete(int id) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM tutorial WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
}