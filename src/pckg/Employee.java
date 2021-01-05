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

public class Employee extends DB_Queries{
	
	private String Full_name;
	private String Phone_number;
	private int ID;
	
	Connection connect;
	PreparedStatement pr = null;
	ResultSet rs = null;
	
	public Employee(String name, int ID, String phone) {
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
	
	public int add_employee(String[] keys){
		String sql = "INSERT INTO employee(name,ID,phone) VALUES (?,?,?)";
        return Add_to_table(connect, pr, map(), sql, keys);
	}
	
	public boolean update_employee(String Fname, String Lname, int ID, String PHnumber) {
		
		return true;
	}
	public boolean delete_employee( ) {
		
		return true;
	}
	public void search_employee() {
		
	}
	
	public Object[][] load_table() {
		String table = "employee";
		ArrayList<Employee> t = load_employee_table(connect, rs, table, 3);
		Object[][] tasks = new Object[t.size()][3];
		for(int i=0; i<t.size(); i++) {
			tasks[i][0] = t.get(i).get_name();
			tasks[i][1] = t.get(i).get_ID();
			tasks[i][2] = t.get(i).get_phonenumber();
		}
		return tasks;
	}
	
	public Map<String, String> map()
	{ 
		Map<String, String> row = new HashMap<String, String>();
		row.put("name", Full_name); // title
		row.put("ID", Integer.toString(ID)); // body
		row.put("phone", Phone_number); // employee
		return row;
	}
	
	public String get_name() {
		return Full_name;
	}

	public String get_ID() {
		return Integer.toString(ID);
	}

	public String get_phonenumber() {
		return Phone_number;
	}
}
