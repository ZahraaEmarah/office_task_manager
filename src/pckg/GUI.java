package pckg;

import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.Color;
import javax.swing.JTextField;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JSeparator;

public class GUI {

	private JFrame frame;
	private final JPanel login = new JPanel();
	private JPasswordField passwordField;
	private JTextField textField;
	login lg = new login();

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

		JPanel toolbar = new JPanel();
		toolbar.setBounds(0, 0, 152, 570);
		Home.add(toolbar);
		toolbar.setLayout(null);

		JButton btnNewButton = new JButton("Assign Task");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(10, 128, 132, 23);
		toolbar.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Add Employee");
		btnNewButton_1.setBounds(10, 209, 132, 23);
		toolbar.add(btnNewButton_1);

		JButton btnViewTasks = new JButton("View Tasks");
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
		btnViewDeadlines.setBounds(10, 290, 132, 23);
		toolbar.add(btnViewDeadlines);

		JLabel lblTasksDue = new JLabel("2 tasks due today");
		lblTasksDue.setHorizontalAlignment(SwingConstants.CENTER);
		lblTasksDue.setForeground(new Color(204, 0, 0));
		lblTasksDue.setBounds(10, 324, 132, 14);
		toolbar.add(lblTasksDue);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(162, 76, 500, 483);
		Home.add(textArea);

		JPanel panel = new JPanel();
		panel.setBounds(151, 0, 521, 65);
		Home.add(panel);
		panel.setLayout(null);

		textField = new JTextField();
		textField.setBounds(10, 34, 187, 20);
		panel.add(textField);
		textField.setColumns(10);

		JButton btnDelete = new JButton(new ImageIcon("/Office_manager/delete.png"));
		btnDelete.setBounds(478, 34, 33, 23);
		panel.add(btnDelete);

		JButton btnSearch = new JButton(new ImageIcon("/Office_manager/search.png"));
		btnSearch.setBounds(203, 34, 33, 20);
		panel.add(btnSearch);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(379, 34, 89, 23);
		panel.add(btnUpdate);

		JButton btnSubmitTask = new JButton("Submit task");
		btnSubmitTask.setBounds(273, 34, 102, 23);
		panel.add(btnSubmitTask);
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
					
					if (lg.Logging(passwordField.getText())) {
						login.setVisible(false);
						Home.setVisible(true);
						lblHeyManager.setText("Hello " + lg.get_name());
						
					}else
					{
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
