package dao;

import model.Admin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class AdminDAO extends DAO {	
	public AdminDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	public boolean insert(Admin admin) {
		boolean status = false;
		try {
			String sql = "INSERT INTO admin (nome, senha)"
		               + "VALUES ('" + admin.getNome()  + "', '"
		                             + admin.getSenha() + "' );";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	public Admin getByPK(String nome) {
		Admin admin = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM admin WHERE nome = '" + nome + "'";
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 admin = new Admin(rs.getString("nome"), rs.getString("senha"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return admin;
	}
	
	
	public List<Admin> get() {
		return get("");
	}
	
	public List<Admin> getOrderByNome() {
		return get("nome");		
	}
	
	public List<Admin> getOrderBySenha() {
		return get("senha");		
	}
	
	private List<Admin> get(String orderBy) {
		List<Admin> admins = new ArrayList<Admin>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM admin" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Admin p = new Admin(rs.getString("nome"), rs.getString("senha"));
	            admins.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return admins;
	}
	
	
	public boolean update(Admin admin) {
		boolean status = false;
		try {  
			String sql = "UPDATE admin SET nome = '" + admin.getNome() + "', "
					   + "senha = '" + admin.getSenha() + "'"
					   + "WHERE nome = '" + admin.getNome() + "'";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public boolean delete(String nome) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM admin WHERE nome = '" + nome + "'");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
}