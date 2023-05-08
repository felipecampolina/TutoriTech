package dao;

import java.sql.*;

public class DAO {
	protected Connection conexao;
	
	public DAO() {
		conexao = null;
	}

	public boolean conectar() {
		String driverName = "postgres.database.azure.com";                    
		String serverName = "tutoritech-bd";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + "." + driverName + ":" + porta +"/" + "postgres";
		String username = "tutoritech_adm";
		String password = "admin_123";
		boolean status = false;

		try {
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Conexão efetuada com o postgres!");
		} catch (SQLException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
		}

		return status;
	}
	
	public boolean close() {
		boolean status = false;
		
		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}
}