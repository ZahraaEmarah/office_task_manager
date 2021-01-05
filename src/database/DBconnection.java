package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {

	private static final String pin = "0000";
	private static final String CONN = "jdbc:mysql://localhost/login";
	private static final String SQCONN = "jdbc:sqlite:office.sqlite";
	
	public static Connection get_connection() throws SQLException{
		
		try {
			
			Class.forName("org.sqlite.JDBC");
			return DriverManager.getConnection(SQCONN);
			
		}catch(ClassNotFoundException ex){
			ex.printStackTrace();
		}
		
		return null;
	}

}
