package dao;

import model.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class UsuarioDAO extends DAO {	
	public UsuarioDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	
	public boolean insert(Usuario usuario) {
		boolean status = false;
		try {
			String sql = "INSERT INTO usuario (email, nome, telefone, senha, cep, cidade, rua, numero, complemento)"
		               + "VALUES ('" + usuario.getEmail() +"', '" +usuario.getNome() +"', '" + usuario.getTelefone()+ "', '"
					   + usuario.getSenha()+ "', '"+ usuario.getCep() +"', '"+usuario.getCidade()+ "', '"+usuario.getRua()+ "', '"
		               +usuario.getNumero()+ "', '" + usuario.getComplemento()+  "');";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	public Usuario getByPK(String email) {
		Usuario usuario = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM usuario WHERE email = '" + email + "'";
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 usuario = new Usuario(rs.getString("email"), rs.getString("nome"), rs.getString("telefone"), 
	        			               rs.getString("senha"), rs.getString("cep"), rs.getString("cidade"),
	        			               rs.getString("rua"), rs.getInt("numero"), rs.getString("complemento"));
	        	 
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		return usuario;
	}
	
	
	public List<Usuario> get() {
		return get("");
	}

	
	public List<Usuario> getOrderByEmail() {
		return get("email");		
	}
	
	
	public List<Usuario> getOrderByNome() {
		return get("nome");		
	}
	
	private List<Usuario> get(String orderBy) {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM usuario" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Usuario u  = new Usuario(rs.getString("email"), rs.getString("nome"),rs.getString("telefone"), 
	        			                 rs.getString("senha"), rs.getString("cep"), rs.getString("cidade"),
			                             rs.getString("rua"), rs.getInt("numero"), rs.getString("complemento"));
	            usuarios.add(u);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return usuarios;
	}
	
	
	public boolean update(Usuario usuario) {
		boolean status = false;
		try {  
			String sql = "UPDATE usuario SET nome = '" + usuario.getNome() + "', " 
		               + "telefone = '" + usuario.getTelefone()+ "', " 
					   + "senha= '" + usuario.getSenha() + "', " 
		               + "cep= '" + usuario.getCep() + "', " 
					   + "cidade= '" + usuario.getCidade()+ "', "
		               + "rua= '" + usuario.getRua() + "', "
		               + "numero= '" + usuario.getNumero() + "', "
		               + "complemento= '" + usuario.getComplemento() + "'"
					   + " WHERE email = '" + usuario.getEmail() + "'" ;
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public boolean delete(String email) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM usuario WHERE email = '" + email + "'");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
}