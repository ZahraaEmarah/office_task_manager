package pckg;

import java.awt.EventQueue;

import javax.imageio.ImageIO;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.JTable;

public class GUI {

	private JFrame frame;
	private final JPanel login = new JPanel();
	private JPasswordField passwordField;
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
	private String[] task_db = { "get", "sth", "from", "db" };
	private String[] employee_db = { "get", "sth", "from", "db" };
	Task task_obj ;
	Employee emp_obj;
	protected Map<String, String> emp_entry = new HashMap<String, String>();
	String[] emp_key = { "name", "ID", "phone" };
	protected Map<String, String> task_entry = new HashMap<String, String>();
	String[] task_key = { "title", "body", "employee", "start_date", "due_date", "status", "submission_date" };
	private JTable table;
	Object[][] tasks;
	Object[][] employees;

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

		viewpanel = new JPanel();
		viewpanel.setBounds(162, 76, 500, 483);
		Home.add(viewpanel);
		viewpanel.setLayout(null);
		viewpanel.setVisible(false);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(193, 11, 124, 29);
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
				emp_obj = new Employee(textField_1.getText(), Integer.parseInt(textField_2.getText()), textField_3.getText());
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

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				viewpanel.setVisible(false);
				employeepanel.setVisible(false);
				taskpanel.setVisible(false);
			}
		});
		btnExit.setBounds(401, 11, 89, 23);
		employeepanel.add(btnExit);

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

		JButton button = new JButton("Exit");
		button.setBounds(401, 11, 89, 23);
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
				task_obj = new Task(taskcomboBox.getSelectedItem().toString(), textArea.getText(), employeecomboBox.getSelectedItem().toString()
						, "5/1/2021", "5/1/2021", "0", "5/1/2021");
				int e = task_obj.assign_task(task_key);
				if (e == 1)
					JOptionPane.showMessageDialog(frame, "Possible duplicate of task!", "Error",
							JOptionPane.ERROR_MESSAGE);
				else {
					JOptionPane.showMessageDialog(frame, "New Task added successfully!", "Success",
							JOptionPane.INFORMATION_MESSAGE);
				}
				
				tasks = task_obj.load_table();
			}
		});
		btnAdd.setBounds(386, 449, 89, 23);
		taskpanel.add(btnAdd);

		JPanel toolbar = new JPanel();
		toolbar.setBounds(0, 0, 152, 570);
		Home.add(toolbar);
		toolbar.setLayout(null);

		JPanel toolbarpanel = new JPanel();
		toolbarpanel.setBounds(162, 11, 500, 54);
		Home.add(toolbarpanel);
		toolbarpanel.setLayout(null);

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
				top_panel = "task";
				btnSubmitTask.setVisible(true);
				
				// get tasks from DB
				task_obj = new Task("","","","","","","");
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

		JLabel lblHour = new JLabel("Hour");
		lblHour.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblHour.setHorizontalAlignment(SwingConstants.CENTER);
		lblHour.setBounds(10, 11, 132, 41);
		toolbar.add(lblHour);

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
				
				emp_obj = new Employee("",0,"");
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
		lblHeyManager.setHorizontalAlignment(SwingConstants.CENTER);
		lblHeyManager.setBounds(10, 72, 132, 14);
		toolbar.add(lblHeyManager);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 277, 132, 2);
		toolbar.add(separator_1);

		JButton btnViewDeadlines = new JButton("View deadlines");
		btnViewDeadlines.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSubmitTask.setVisible(false);
			}
		});
		btnViewDeadlines.setBounds(10, 290, 132, 23);
		toolbar.add(btnViewDeadlines);

		JLabel lblTasksDue = new JLabel("2 tasks due today");
		lblTasksDue.setHorizontalAlignment(SwingConstants.CENTER);
		lblTasksDue.setForeground(new Color(204, 0, 0));
		lblTasksDue.setBounds(10, 324, 132, 14);
		toolbar.add(lblTasksDue);

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
		btnLogOut.setBounds(10, 362, 132, 23);
		toolbar.add(btnLogOut);

		textField = new JTextField();
		textField.setBounds(6, 23, 187, 20);
		toolbarpanel.add(textField);
		textField.setColumns(10);

		JButton btnDelete = new JButton(new ImageIcon("/Office_manager/delete.png"));
		btnDelete.setBounds(457, 23, 33, 23);
		toolbarpanel.add(btnDelete);

		JButton btnSearch = new JButton(new ImageIcon("/Office_manager/search.png"));
		btnSearch.setBounds(203, 23, 33, 20);
		toolbarpanel.add(btnSearch);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (top_panel.equals("employee")) {
					viewpanel.setVisible(false);
					employeepanel.setVisible(true);
					taskpanel.setVisible(false);
				} else if (top_panel.equals("task")) {
					viewpanel.setVisible(false);
					employeepanel.setVisible(false);
					taskpanel.setVisible(true);
					lblNewTask.setText("Edit task");
				} else {
					JOptionPane.showMessageDialog(frame, "Please select an entry!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnUpdate.setBounds(370, 22, 77, 23);
		toolbarpanel.add(btnUpdate);

		btnSubmitTask = new JButton("Submit task");
		btnSubmitTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				task_obj.submit_task(task_entry, task_key);
			}
		});
		btnSubmitTask.setBounds(246, 22, 114, 23);
		toolbarpanel.add(btnSubmitTask);
		btnSubmitTask.setVisible(false);
		Home.setVisible(false);

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
