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

public class Task extends DB_Queries {

	private String title;
	private String des;
	private String creation_date;
	private String submission_date;
	private String deadline;
	private String employee_name;
	private String status;

	Connection connect;
	PreparedStatement pr = null;
	ResultSet rs = null;

	public Task(String title, String des, String employee, String creation_date, String deadline, String status,
			String submission_date) {
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
	}

	public boolean isConnected() {
		return this.connect != null;
	}

	public int assign_task(String[] keys) {
		String sql = "INSERT INTO task(title,body,employee,start_date,due_date,status,submission_date) VALUES (?,?,?,?,?,?,?)";
		return Add_to_table(connect, pr, map(), sql, keys);
	}

	public int submit_task(Map<String, String> row, String[] keys) {
		String sql = "UPDATE task SET status = ?" + " where title = ?, employee=?, due_date=?";
		row.replace("status", "1");
		return Update_entry(connect, pr, row, sql, keys);
	}

	public Object[][] load_table() {
		String table = "task";
		ArrayList<Task> t = load_task_table(connect, rs, table, 7);
		Object[][] tasks = new Object[t.size()][7];
		for(int i=0; i<t.size(); i++) {
			System.out.println(t.get(i).get_title()+ "\n" + t.get(i).get_due_date()+ "\n" + t.get(i).get_employee_name());
			tasks[i][0] = t.get(i).get_title();
			tasks[i][1] = t.get(i).get_body();
			tasks[i][2] = t.get(i).get_employee_name();
			tasks[i][3] = t.get(i).get_start_date();
			tasks[i][4] = t.get(i).get_due_date();
			tasks[i][5] = t.get(i).get_status();
			tasks[i][6] = t.get(i).get_submission_date();
		}
		return tasks;
	}

	public void search_task() {

	}

	public Map<String, String> map() {
		Map<String, String> row = new HashMap<String, String>();
		row.put("title", title); // title
		row.put("body", des); // body
		row.put("employee", employee_name); // employee
		row.put("start_date", "5/1/2021");
		row.put("due_date", "5/1/2021");
		row.put("status", "0");
		row.put("submission_date", "5/1/2021");
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

}
