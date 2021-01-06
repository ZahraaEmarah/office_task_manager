package pckg;

import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;

import java.awt.Color;
import javax.swing.JTextField;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JRadioButton;

public class GUI {

	private JFrame frame;
	private final JPanel login = new JPanel();
	private JPasswordField passwordField;
	private JPasswordField PINField;
	private JTextField textField;
	login lg = new login();
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private String top_panel = "";
	private JPanel employeepanel;
	private JPanel taskpanel;
	private JPanel viewpanel;
	private JButton btnSubmitTask;
	private String[] task_db = { "إعداد تقارير", "عمل خطط إسبوعية وشهري ة لتنظيم العم ل", "الردود على المكاتبات الواردة من الجهات الاعلى", "إ عداد يوميات التمام للعاملين", "other" };
	private String[] employee_db = { "get", "sth", "from", "db" };
	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
	Date date = new Date(System.currentTimeMillis());
	Task task_obj;
	Employee emp_obj;
	Manager mg;
	protected Map<String, String> emp_entry = new HashMap<String, String>();
	String[] emp_key = { "name", "ID", "phone" };
	protected Map<String, String> task_entry = new HashMap<String, String>();
	String[] task_key = { "title", "body", "employee", "start_date", "due_date", "status", "submission_date", "id" };
	private JTable table;
	Object[][] tasks;
	Object[][] employees;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField start;
	private JTextField due;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
		if (this.lg.isConnected()) {
			System.out.println("CONNECTED");
		} else {
			System.out.println("DISCONNECTED");
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 688, 609);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel Home = new JPanel();
		Home.setBackground(new Color(0, 102, 102));
		Home.setBounds(0, 0, 672, 570);
		frame.getContentPane().add(Home);
		Home.setLayout(null);

		taskpanel = new JPanel();
		taskpanel.setBounds(162, 76, 500, 483);
		Home.add(taskpanel);
		taskpanel.setLayout(null);
		taskpanel.setVisible(false);

		JLabel lblNewTask = new JLabel("New Task");
		lblNewTask.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewTask.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewTask.setBounds(201, 11, 113, 28);
		taskpanel.add(lblNewTask);

		JLabel lblChooseATask = new JLabel("choose a task:");
		lblChooseATask.setBounds(25, 95, 91, 20);
		taskpanel.add(lblChooseATask);

		JLabel lblAssignTo = new JLabel("Assign to:");
		lblAssignTo.setBounds(25, 133, 91, 14);
		taskpanel.add(lblAssignTo);

		JLabel lblSetStartDate = new JLabel("set start date:");
		lblSetStartDate.setBounds(25, 190, 91, 14);
		taskpanel.add(lblSetStartDate);

		JLabel lblSetDueDate = new JLabel("set due date:");
		lblSetDueDate.setBounds(281, 190, 75, 14);
		taskpanel.add(lblSetDueDate);

		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setBounds(25, 231, 91, 14);
		taskpanel.add(lblDescription);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(25, 256, 450, 182);
		taskpanel.add(textArea);

		JButton button = new JButton(new ImageIcon("/Office_manager/close.png"));
		button.setBounds(458, 11, 32, 23);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				viewpanel.setVisible(false);
				employeepanel.setVisible(false);
				taskpanel.setVisible(false);
			}
		});
		taskpanel.add(button);

		JComboBox taskcomboBox = new JComboBox(task_db);
		taskcomboBox.setBounds(126, 95, 218, 20);
		taskpanel.add(taskcomboBox);

		JComboBox employeecomboBox = new JComboBox(employee_db);
		employeecomboBox.setBounds(126, 130, 218, 20);
		taskpanel.add(employeecomboBox);

		// UtilDateModel model = new UtilDateModel();
		// JDatePanelImpl d = new JDatePanelImpl(model);

		JButton btnAdd = new JButton("ADD");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				task_obj = new Task(taskcomboBox.getSelectedItem().toString(), textArea.getText(),
						employeecomboBox.getSelectedItem().toString(), start.getText(), due.getText(), "Ongoing", " ",
						"");
				task_obj.assign_task(task_key);

				JOptionPane.showMessageDialog(frame, "New Task added successfully!", "Success",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnAdd.setBounds(386, 449, 89, 23);
		taskpanel.add(btnAdd);

		start = new JTextField();
		start.setBounds(101, 187, 118, 20);
		taskpanel.add(start);
		start.setColumns(10);

		due = new JTextField();
		due.setColumns(10);
		due.setBounds(357, 187, 118, 20);
		taskpanel.add(due);

		JPanel settings = new JPanel();
		settings.setBounds(162, 76, 500, 483);
		Home.add(settings);
		settings.setLayout(null);
		settings.setVisible(false);

		JLabel lblName = new JLabel("PIN:");
		lblName.setBounds(58, 216, 80, 14);
		settings.add(lblName);

		JLabel label = new JLabel("Name:");
		label.setBounds(58, 116, 105, 14);
		settings.add(label);

		textField_4 = new JTextField();
		textField_4.setBounds(58, 141, 230, 20);
		settings.add(textField_4);
		textField_4.setColumns(10);

		PINField = new JPasswordField();
		PINField.setBounds(58, 240, 105, 20);
		settings.add(PINField);
		PINField.setColumns(10);

		JLabel lblProfile = new JLabel("Profile");
		lblProfile.setHorizontalAlignment(SwingConstants.CENTER);
		lblProfile.setFont(new Font("Tahoma", Font.PLAIN, 33));
		lblProfile.setBounds(189, 11, 112, 37);
		settings.add(lblProfile);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Manager mg = new Manager(textField_4.getText(), PINField.getText());
				mg.Change_name();
				mg.Change_PIN();
			}
		});
		btnSave.setBounds(199, 331, 89, 23);
		settings.add(btnSave);

		viewpanel = new JPanel();
		viewpanel.setBounds(162, 76, 500, 483);
		Home.add(viewpanel);
		viewpanel.setVisible(false);
		viewpanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(193, 11, 124, 29);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		viewpanel.add(lblNewLabel);

		employeepanel = new JPanel();
		employeepanel.setBounds(162, 76, 500, 483);
		Home.add(employeepanel);
		employeepanel.setLayout(null);
		employeepanel.setVisible(false);

		JLabel lblNewEmployee = new JLabel("New Employee");
		lblNewEmployee.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewEmployee.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewEmployee.setBounds(180, 11, 144, 28);
		employeepanel.add(lblNewEmployee);

		JLabel lblEnterEmployeesName = new JLabel("Employee's Full name:");
		lblEnterEmployeesName.setBounds(33, 107, 138, 14);
		employeepanel.add(lblEnterEmployeesName);

		textField_1 = new JTextField();
		textField_1.setBounds(33, 132, 262, 20);
		employeepanel.add(textField_1);
		textField_1.setColumns(10);

		JLabel lblEmployeesId = new JLabel("Employee's ID:");
		lblEmployeesId.setBounds(33, 213, 138, 14);
		employeepanel.add(lblEmployeesId);

		textField_2 = new JTextField();
		textField_2.setBounds(33, 238, 262, 20);
		employeepanel.add(textField_2);
		textField_2.setColumns(10);

		JLabel lblEmployeesPhoneNumber = new JLabel("Employee's phone number");
		lblEmployeesPhoneNumber.setBounds(33, 319, 262, 14);
		employeepanel.add(lblEmployeesPhoneNumber);

		textField_3 = new JTextField();
		textField_3.setBounds(33, 344, 262, 20);
		employeepanel.add(textField_3);
		textField_3.setColumns(10);

		JButton btnAdd_1 = new JButton("ADD");
		btnAdd_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emp_obj = new Employee(textField_1.getText(), textField_2.getText(), textField_3.getText());
				int err = emp_obj.add_employee(emp_key);
				if (err == 1)
					JOptionPane.showMessageDialog(frame, "Possible duplicate employee!", "Error",
							JOptionPane.ERROR_MESSAGE);
				else {
					JOptionPane.showMessageDialog(frame, "Employee record added successfully!", "Success",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnAdd_1.setBounds(216, 430, 89, 23);
		employeepanel.add(btnAdd_1);

		JButton btnExit = new JButton(new ImageIcon("/Office_manager/close.png"));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				viewpanel.setVisible(false);
				employeepanel.setVisible(false);
				taskpanel.setVisible(false);
			}
		});
		btnExit.setBounds(458, 11, 32, 23);
		employeepanel.add(btnExit);

		JPanel toolbar = new JPanel();
		toolbar.setBounds(0, 0, 152, 570);
		Home.add(toolbar);
		toolbar.setLayout(null);

		JPanel toolbarpanel = new JPanel();
		toolbarpanel.setBounds(162, 0, 500, 65);
		Home.add(toolbarpanel);
		toolbarpanel.setLayout(null);

		btnSubmitTask = new JButton("Submit task");
		btnSubmitTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				task_obj = new Task(table.getValueAt(row, 0).toString(), table.getValueAt(row, 1).toString(),
						table.getValueAt(row, 2).toString(), table.getValueAt(row, 3).toString(),
						table.getValueAt(row, 4).toString(), table.getValueAt(row, 5).toString(),
						table.getValueAt(row, 6).toString(), table.getValueAt(row, 7).toString());
				task_obj.submit_task(task_entry, task_key);
			}
		});
		btnSubmitTask.setBounds(246, 33, 114, 23);
		toolbarpanel.add(btnSubmitTask);

		JRadioButton rdbtnEmployees = new JRadioButton("Employees");
		rdbtnEmployees.setBounds(67, 7, 90, 23);
		toolbarpanel.add(rdbtnEmployees);

		JRadioButton rdbtnTasks = new JRadioButton("Tasks");
		rdbtnTasks.setBounds(154, 7, 109, 23);
		toolbarpanel.add(rdbtnTasks);
		btnSubmitTask.setVisible(false);
		Home.setVisible(false);

		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnEmployees);
		group.add(rdbtnTasks);

		JButton btnNewButton = new JButton("Assign Task");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewpanel.setVisible(false);
				employeepanel.setVisible(false);
				taskpanel.setVisible(true);
				top_panel = "";
				lblNewTask.setText("New task");
				btnSubmitTask.setVisible(false);
			}
		});
		btnNewButton.setBounds(10, 128, 132, 23);
		toolbar.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Add Employee");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				taskpanel.setVisible(false);
				viewpanel.setVisible(false);
				employeepanel.setVisible(true);
				top_panel = "";
				btnSubmitTask.setVisible(false);
			}
		});
		btnNewButton_1.setBounds(10, 209, 132, 23);
		toolbar.add(btnNewButton_1);

		JButton btnViewTasks = new JButton("View Tasks");
		btnViewTasks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				taskpanel.setVisible(false);
				employeepanel.setVisible(false);
				viewpanel.setVisible(true);
				settings.setVisible(false);
				top_panel = "task";
				btnSubmitTask.setVisible(true);

				// get tasks from DB
				task_obj = new Task("", "", "", "", "", "", "", "");
				tasks = task_obj.load_table();

				table = new JTable(tasks, task_key);
				table.setBounds(10, 12, 480, 419);
				viewpanel.add(table);

				JScrollPane scrollPane = new JScrollPane(table);
				scrollPane.setBounds(10, 12, 480, 419);
				viewpanel.add(scrollPane);

			}
		});
		btnViewTasks.setBounds(10, 162, 132, 23);
		toolbar.add(btnViewTasks);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 196, 132, 2);
		toolbar.add(separator);

		JButton btnViewEmployees = new JButton("View Employees");
		btnViewEmployees.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				top_panel = "employee";
				viewpanel.setVisible(true);
				employeepanel.setVisible(false);
				taskpanel.setVisible(false);
				btnSubmitTask.setVisible(false);

				emp_obj = new Employee("", "", "");
				employees = emp_obj.load_table();

				table = new JTable(employees, emp_key);
				table.setBounds(10, 12, 480, 419);
				viewpanel.add(table);

				JScrollPane scrollPane = new JScrollPane(table);
				scrollPane.setBounds(10, 12, 480, 419);
				viewpanel.add(scrollPane);
			}
		});
		btnViewEmployees.setBounds(10, 243, 132, 23);
		toolbar.add(btnViewEmployees);

		JLabel lblHeyManager = new JLabel("");
		lblHeyManager.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblHeyManager.setHorizontalAlignment(SwingConstants.CENTER);
		lblHeyManager.setBounds(10, 72, 132, 14);
		toolbar.add(lblHeyManager);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 277, 132, 2);
		toolbar.add(separator_1);

		JLabel lblTasksDue = new JLabel("2 tasks due today");
		lblTasksDue.setHorizontalAlignment(SwingConstants.CENTER);
		lblTasksDue.setForeground(new Color(204, 0, 0));
		lblTasksDue.setBounds(10, 324, 132, 14);
		toolbar.add(lblTasksDue);

		JButton btnViewDeadlines = new JButton("View deadlines");
		btnViewDeadlines.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSubmitTask.setVisible(false);
				mg = new Manager("", "");
				tasks = mg.Notify_manager(date);
				System.out.println(tasks.length);
				lblTasksDue.setText(tasks.length + " tasks due today");

				table = new JTable(tasks, task_key);
				table.setBounds(10, 12, 480, 419);
				viewpanel.add(table);

				JScrollPane scrollPane = new JScrollPane(table);
				scrollPane.setBounds(10, 12, 480, 419);
				viewpanel.add(scrollPane);
			}
		});
		btnViewDeadlines.setBounds(10, 290, 132, 23);
		toolbar.add(btnViewDeadlines);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 349, 132, 2);
		toolbar.add(separator_2);

		JButton btnLogOut = new JButton("Log out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Home.setVisible(false);
				login.setVisible(true);
				passwordField.setText("");
			}
		});
		btnLogOut.setBounds(10, 396, 132, 23);
		toolbar.add(btnLogOut);

		JButton btnSettings = new JButton("Settings");
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				taskpanel.setVisible(false);
				employeepanel.setVisible(false);
				viewpanel.setVisible(false);
				settings.setVisible(true);
			}
		});
		btnSettings.setBounds(10, 362, 132, 23);
		toolbar.add(btnSettings);

		textField = new JTextField();
		textField.setBounds(6, 34, 187, 20);
		toolbarpanel.add(textField);
		textField.setColumns(10);

		JButton btnDelete = new JButton(new ImageIcon("/Office_manager/delete.png"));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				if (top_panel.equals("task")) {
					if (row == -1) {
						JOptionPane.showMessageDialog(frame, "Please select an entry!", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						for (int j = 0; j < 3; j++) {
							System.out.print(table.getValueAt(row, j) + " ");
						}
						System.out.println();
						task_obj = new Task("", "", "", "", "", "", "", "");
						task_obj.delete_task(table.getValueAt(row, 7).toString());
						JOptionPane.showMessageDialog(frame, "Employee record deleted successfully!", "Success",
								JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					if (row == -1) {
						JOptionPane.showMessageDialog(frame, "Please select an entry!", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						for (int j = 0; j < 3; j++) {
							System.out.print(table.getValueAt(row, j) + " ");
						}
						System.out.println();
						emp_obj = new Employee("", "", "");
						emp_obj.delete_employee(table.getValueAt(row, 1).toString());
						JOptionPane.showMessageDialog(frame, "Employee record deleted successfully!", "Success",
								JOptionPane.INFORMATION_MESSAGE);
					}

				}
			}
		});
		btnDelete.setBounds(457, 33, 33, 23);
		toolbarpanel.add(btnDelete);

		JButton btnSearch = new JButton(new ImageIcon("/Office_manager/search.png"));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (rdbtnTasks.isSelected()) {

					task_obj = new Task("", "", "", "", "", "", "", "");
					tasks = task_obj.search_task(textField.getText());

					if (tasks.length <= 0) {
						JOptionPane.showMessageDialog(frame, "No results found", "Error", JOptionPane.ERROR_MESSAGE);
					}

					table = new JTable(tasks, task_key);
					table.setBounds(10, 12, 480, 419);
					viewpanel.add(table);
					viewpanel.setVisible(true);

				} else if (rdbtnEmployees.isSelected()) {

					emp_obj = new Employee("", "", "");
					employees = emp_obj.search_employee(textField.getText());

					if (employees.length <= 0) {
						JOptionPane.showMessageDialog(frame, "No results found", "Error", JOptionPane.ERROR_MESSAGE);
					}


					table = new JTable(employees, emp_key);
					table.setBounds(10, 12, 480, 419);
					viewpanel.add(table);
					viewpanel.setVisible(true);

				} else {
					JOptionPane.showMessageDialog(frame, "Please select a category", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

				JScrollPane scrollPane = new JScrollPane(table);
				scrollPane.setBounds(10, 12, 480, 419);
				viewpanel.add(scrollPane);

			}
		});
		btnSearch.setBounds(203, 34, 33, 20);
		toolbarpanel.add(btnSearch);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(frame, "Please select an entry!", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					System.out.println("row is " + row);
					int column = table.getColumnCount();
					for (int j = 0; j < column; j++) {
						System.out.print(table.getValueAt(row, j) + " ");
					}
					System.out.println();

					if (top_panel.equals("task")) {
						task_obj = new Task(table.getValueAt(row, 0).toString(), table.getValueAt(row, 1).toString(),
								table.getValueAt(row, 2).toString(), table.getValueAt(row, 3).toString(),
								table.getValueAt(row, 4).toString(), table.getValueAt(row, 5).toString(),
								table.getValueAt(row, 6).toString(), table.getValueAt(row, 7).toString());

						task_obj.update_task(task_key);
					} else if (top_panel.equals("employee")) {

						emp_obj = new Employee(table.getValueAt(row, 0).toString(), table.getValueAt(row, 2).toString(),
								table.getValueAt(row, 1).toString());

						emp_obj.update_employee(emp_key, table.getValueAt(row, 2).toString());
					}
				}
			}
		});
		btnUpdate.setBounds(370, 33, 77, 23);
		toolbarpanel.add(btnUpdate);

		JLabel lblSearchIn = new JLabel("Search in:");
		lblSearchIn.setBounds(6, 11, 68, 14);
		toolbarpanel.add(lblSearchIn);

		login.setBackground(new Color(0, 102, 102));
		login.setBounds(0, 0, 672, 570);
		frame.getContentPane().add(login);
		login.setLayout(null);

		passwordField = new JPasswordField(10);
		passwordField.setBounds(237, 274, 195, 32);
		login.add(passwordField);
		passwordField.setColumns(10);

		BufferedImage myPicture;
		try {
			myPicture = ImageIO.read(new File("/Office_manager/login.png"));
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			picLabel.setBounds(237, 42, 195, 195);
			login.add(picLabel);
		} catch (IOException e) {
			e.printStackTrace();
		}

		JButton btnEnter = new JButton("LOGIN");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (lg.Login(passwordField.getText())) {
						login.setVisible(false);
						Home.setVisible(true);
						lblHeyManager.setText("Hello " + lg.get_name());
						mg = new Manager("", "");
						tasks = mg.Notify_manager(date);
						System.out.println(tasks.length);
						lblTasksDue.setText(tasks.length + " tasks due today");

					} else {
						JOptionPane.showMessageDialog(frame, "Wrong PIN", "Error", JOptionPane.ERROR_MESSAGE);
						passwordField.setText("");
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnEnter.setBounds(289, 327, 89, 23);
		login.add(btnEnter);

	}
}
