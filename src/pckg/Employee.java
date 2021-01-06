package pckg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import database.DB_Queries;
import database.DBconnection;

public class Employee extends DB_Queries {

	private String Full_name;
	private String Phone_number;
	private String ID;

	Connection connect;
	PreparedStatement pr = null;
	ResultSet rs = null;

	public Employee(String name, String ID, String phone) {
		try {
			this.connect = DBconnection.get_connection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (this.connect == null)
			System.exit(1);

		this.Full_name = name;
		this.ID = ID;
		this.Phone_number = phone;
	}

	public int add_employee(String[] keys) {
		String sql = "INSERT INTO employee(name,ID,phone) VALUES (?,?,?)";
		return Add_to_table(connect, pr, map(), sql, keys);
	}

	public int update_employee(String[] keys, String old_id) {
		String sql = "UPDATE employee SET name = ?, ID = ?, phone = ? " + " WHERE ID = ?";
		int ret = Update_entry(connect, pr, map(), sql, keys, "employee", Integer.parseInt(old_id));
		close_connect();
		return ret;
	}

	public int delete_employee(String primary_key) {
		String sql = "DELETE FROM employee where ID LIKE ? ";
		int ret = delete_entry(connect, pr, sql, Integer.parseInt(primary_key));
		close_connect();
		return ret;
	}

	public Object[][] search_employee(String search_word) {
		String sql = "SELECT * FROM employee WHERE name = ?";
		ArrayList<Employee> t = fetch_entry(connect, pr, rs, sql, "employee", search_word);
		Object[][] tasks = new Object[t.size()][3];
		for (int i = 0; i < t.size(); i++) {
			tasks[i][0] = t.get(i).get_name();
			tasks[i][1] = t.get(i).get_ID();
			tasks[i][2] = t.get(i).get_phonenumber();
		}
		return tasks;

	}

	public Object[][] load_table() {
		String table = "employee";
		ArrayList<Employee> t = load_employee_table(connect, rs, table, 3);
		Object[][] tasks = new Object[t.size()][3];
		for (int i = 0; i < t.size(); i++) {
			tasks[i][0] = t.get(i).get_name();
			tasks[i][1] = t.get(i).get_ID();
			tasks[i][2] = t.get(i).get_phonenumber();
		}
		return tasks;
	}

	public ArrayList<String> get_names() {
		String sql = "SELECT name FROM employee";
		ArrayList<String> names = new ArrayList<String>();
		try {
			pr = connect.prepareStatement(sql);
			rs = pr.executeQuery();

			while (rs.next()) {
				names.add(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return names;
	}

	public Map<String, String> map() {
		Map<String, String> row = new HashMap<String, String>();
		row.put("name", Full_name); // title
		row.put("ID", ID); // body
		row.put("phone", Phone_number); // employee
		return row;
	}

	public String get_name() {
		return Full_name;
	}

	public String get_ID() {
		return ID;
	}

	public String get_phonenumber() {
		return Phone_number;
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
