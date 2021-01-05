package pckg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.DBconnection;

public class login {

	Connection connect;
	private String name = "user";

	public login() {

		try {
			this.connect = DBconnection.get_connection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (this.connect == null)
			System.exit(1);
	}

	public boolean isConnected() {
		return this.connect != null;
	}

	public boolean Login(String PIN) throws Exception {
		PreparedStatement pr = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM login where PIN =?";

		try {
			pr = this.connect.prepareStatement(sql);
			pr.setString(1, PIN);

			rs = pr.executeQuery();
			name = rs.getString(2);
			set_name(name);

			if (rs.next()) {
				return true;
			}

			return false;
		} catch (SQLException x) {
			return false;
		}

		finally {
			pr.close();
			rs.close();
		}
	}

	public void set_name(String n) {
		name = n;
	}

	public String get_name() {
		return name;
	}

}
