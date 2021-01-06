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

public class Task extends DB_Queries {

	private String title;
	private String des;
	private String creation_date;
	private String submission_date;
	private String deadline;
	private String employee_name;
	private String status;
	private String id;
	
	Connection connect;
	PreparedStatement pr = null;
	ResultSet rs = null;

	public Task(String title, String des, String employee, String creation_date, String deadline, String status,
			String submission_date, String id) {
		try {
			this.connect = DBconnection.get_connection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (this.connect == null)
			System.exit(1);

		this.title = title;
		this.des = des;
		this.employee_name = employee;
		this.creation_date = creation_date;
		this.deadline = deadline;
		this.status = status;
		this.submission_date = submission_date;
		this.id = id;
	}

	public boolean isConnected() {
		return this.connect != null;
	}

	public int assign_task(String[] keys) {
		String sql = "INSERT INTO task(title,body,employee,start_date,due_date,status,submission_date) VALUES (?,?,?,?,?,?,?)";
		status = "Ongoing";
		int ret =  Add_to_table(connect,pr, map(), sql, keys);
		close_connect();
		return ret;
	}

	public int submit_task(Map<String, String> row, String[] keys) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date(System.currentTimeMillis());
		status = "Completed";
		submission_date = formatter.format(date);
		int ret = update_task(keys);
		close_connect();
		return ret;
	}

	public Object[][] load_table() {
		String table = "task";
		ArrayList<Task> t = load_task_table(connect,rs, table, 8);
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
		close_connect();
		return tasks;
	}

	public Object[][] search_task(String search_word) {
		String sql = "SELECT * FROM task WHERE title = ?";
		ArrayList<Task> t = fetch_entry(connect,pr, rs, sql, "task", search_word);
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

	public int delete_task(String primary_key) {
		String sql = "DELETE FROM task where task_id LIKE ? ";
		int ret = delete_entry(connect,pr, sql, Integer.parseInt(primary_key));
		close_connect();
		return ret;
	}
	
	public int update_task(String[] keys) {
		String sql = "UPDATE task SET title = ?, body = ?, employee = ?, start_date = ?, due_date = ?, status = ?, submission_date = ? "
				+ " WHERE task_id = ?";
		
		int ret = Update_entry(connect,pr, map(), sql, keys, "task", Integer.parseInt(id));
		close_connect();
		return ret;
	}

	public Map<String, String> map() {
		Map<String, String> row = new HashMap<String, String>();
		row.put("title", title); // title
		row.put("body", des); // body
		row.put("employee", employee_name); // employee
		row.put("start_date", creation_date);
		row.put("due_date", deadline);
		row.put("status", status);
		row.put("submission_date", submission_date);
		row.put("id", id);
		return row;
	}

	public String get_title() {
		return title;
	}

	public String get_body() {
		return des;
	}

	public String get_employee_name() {
		return employee_name;
	}

	public String get_start_date() {
		return creation_date;
	}

	public String get_due_date() {
		return deadline;
	}

	public String get_status() {
		return status;
	}

	public String get_submission_date() {
		return submission_date;
	}
	
	public String get_id() {
		return id;
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
