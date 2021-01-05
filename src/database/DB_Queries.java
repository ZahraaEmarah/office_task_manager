package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Map;

import pckg.Employee;
import pckg.Task;

public abstract class DB_Queries {

	protected ArrayList<Task> load_task_table(Connection connect, ResultSet rs, String tablename, int index) {
		int count = get_row_count(connect, rs, tablename);
		ArrayList<Task> task_table = new ArrayList<Task>();
		System.out.println("count is " + count);
		String sql = "SELECT * FROM " + tablename;

		try {
			rs = connect.createStatement().executeQuery(sql);
			while (rs.next()) {
				task_table.add(new Task(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
			}
			rs.close();
		} catch (SQLException x) {
			x.printStackTrace();
		}
		return task_table;
	}

	protected ArrayList<Employee> load_employee_table(Connection connect, ResultSet rs, String tablename, int index) {
		int count = get_row_count(connect, rs, tablename);
		ArrayList<Employee> emp_table = new ArrayList<Employee>();
		System.out.println("count is " + count);
		String sql = "SELECT * FROM " + tablename;

		try {
			rs = connect.createStatement().executeQuery(sql);
			while (rs.next()) {
				emp_table.add(new Employee(rs.getString(1), rs.getString(2), rs.getString(3)));
			}
			rs.close();
		} catch (SQLException x) {
			x.printStackTrace();
		}
		return emp_table;
	}

	protected int Add_to_table(Connection connect, PreparedStatement pr, Map<String, String> row, String sql,
			String[] key) {
		// TODO Auto-generated method stub
		try {
			pr = connect.prepareStatement(sql);
			for (int i = 0; i < key.length; i++)
				pr.setString(i + 1, row.get(key[i]));

			pr.execute();
			pr.close();
		} catch (SQLException x) {
			x.printStackTrace();
			return 1;
		}
		return 0;
	}

	protected int Update_entry(Connection connect, PreparedStatement pr, Map<String, String> row, String sql,
			String[] key, String table, int id) {
		// TODO Auto-generated method stub
		try {
			pr = connect.prepareStatement(sql);
			int i;
			for (i = 0; i < key.length; i++)
				pr.setString(i + 1, row.get(key[i]));

			pr.setInt(i, id);	
			pr.executeUpdate();

		} catch (SQLException x) {
			x.printStackTrace();
			return 1;
		} finally {
			if (pr != null) {
				try {
					pr.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return 0;
	}
	
	protected int delete_entry(Connection connect, PreparedStatement pr, String sql, int id) {
		// TODO Auto-generated method stub
		try {
			pr = connect.prepareStatement(sql);
			pr.setInt(1, id);	
			pr.executeUpdate();

		} catch (SQLException x) {
			x.printStackTrace();
			return 1;
		} finally {
			if (pr != null) {
				try {
					pr.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return 0;
	}

	protected int get_row_count(Connection connect, ResultSet rs, String table) {
		String sql = "SELECT COUNT(*) FROM " + table;
		int count = 0;
		try {
			rs = connect.createStatement().executeQuery(sql);
			count = Integer.parseInt(rs.getString(1));
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	protected void set_primary(PreparedStatement pr, String table_name, int counter, Map<String, String> row) {
		if (table_name.equals("task")) {
			try {
				pr.setString(counter, row.get("title"));
				pr.setString(counter + 1, row.get("employee"));
				pr.setString(counter + 2, row.get("due_date"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
