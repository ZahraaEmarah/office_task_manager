package pckg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

	public Object[][] Notify_manager(Date d) {
		
		SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy");
		String search_date = formatter.format(d);
		String sql = "SELECT * FROM task WHERE due_date < ?";
		ArrayList<Task> t = fetch_entry(connect,pr, rs, sql, "task", search_date);
		Object[][] tasks = new Object[t.size()][8];
		for (int i = 0; i < t.size(); i++) {
			tasks[i][0] = t.get(i).get_title();
			tasks[i][1] = t.get(i).get_body();
			tasks[i][2] = t.get(i).get_employee_name();
			tasks[i][3] = t.get(i).get_start_date();
			tasks[i][4] = t.get(i).get_due_date();
			tasks[i][5] = t.get(i).get_status();
			tasks[i][6] = t.get(i).get_submission_date();
			tasks[i][7] = t.get(i).get_id();
		}
		return tasks;
	}

	public int Change_name() {
		String sql = "UPDATE login SET Name = ?, PIN = ?" + " WHERE Name = ?";
		String[] keys = {"Name", "PIN"};
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
