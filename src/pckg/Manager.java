package pckg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import database.DB_Queries;
import database.DBconnection;

public class Manager extends DB_Queries {

	String name;
	String PIN;

	Connection connect;
	PreparedStatement pr = null;
	ResultSet rs = null;

	public Manager(String name, String PIN) {
		this.name = name;
		this.PIN = PIN;

		try {
			this.connect = DBconnection.get_connection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (this.connect == null)
			System.exit(1);
	}

	public void Notify_manager() {

	}

	public int Change_name() {
		String sql = "UPDATE login SET Name = ?, PIN = ?" + " WHERE Name = ?";
		String[] keys = { "Name", "PIN" };
		int ret = Update_entry(connect, pr, map(), sql, keys, "login", 0);
		close_connect();
		return ret;
	}

	public void Change_PIN() {

	}

	public Map<String, String> map() {
		Map<String, String> row = new HashMap<String, String>();
		row.put("Name", name); // title
		row.put("PIN", PIN); // body
		return row;
	}

	private void close_connect() {
		try {
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
