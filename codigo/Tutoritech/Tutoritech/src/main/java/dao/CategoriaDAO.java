package dao;

import model.Categoria;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class CategoriaDAO extends DAO {	
	public CategoriaDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	
	public boolean insert(Categoria categoria) {
		boolean status = false;
		try {
			String sql = "INSERT INTO categoria (nome)"
		               + "VALUES ('" + categoria.getNome() + "');";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	public Categoria get(int id) {
		Categoria categoria = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM categoria WHERE id="+id;
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
	
	public List<Categoria> get() {
		return get("");
	}

	
	public List<Categoria> getOrderByID() {
		return get("id");		
	}
	
	
	public List<Categoria> getOrderByNome() {
		return get("nome");		
	}
	
	private List<Categoria> get(String orderBy) {
		List<Categoria> categorias = new ArrayList<Categoria>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM categoria" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
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
	
	
	public boolean update(Categoria categoria) {
		boolean status = false;
		try {  
			String sql = "UPDATE categoria SET nome = '" + categoria.getNome() + "' WHERE id = " + categoria.getID();
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
			st.executeUpdate("DELETE FROM categoria WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
}