package com.csis.reminder.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.csis.reminder.dao.UserDAO;
import com.csis.reminder.entity.User;
import com.csis.reminder.entity.enumeration.UserType;
import com.csis.reminder.util.MD5Util;
import com.csis.reminder.util.ScreenUtil;
import javax.swing.UIManager;

public class Register extends JFrame {

	private static final long serialVersionUID = -2032331525617462314L;
	private JPanel contentPane;
	private Login login;
	private JButton btnBackToLogin;
	private JLabel lblFirstName;
	private JLabel lblLastName;
	private JLabel lblEmail;
	private JLabel lblPassword;
	private JLabel lblConfirmPassword;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtEmail;
	private JPasswordField txtPassword;
	private JPasswordField txtConfirmPassword;
	private JButton btnRegister;
	private JLabel lblRequiredFields;

	/**
	 * Create the frame.
	 */
	public Register(Login login) {
		this.login = login;
		init();
		ScreenUtil.centerWindow(this);
		setTitle("New User");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		createActions();
	}

	/**
	 * Method to go back to login
	 */
	private void backToLogin() {
		if (!login.isVisible()) {
			login.setVisible(true);
			setVisible(false);
		}
	}

	private void createActions() {
		// Action to Return to Login Page
		btnBackToLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				backToLogin();
			}

		});


		// Actions to Attempt to Register New User
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				UserDAO userDAO = new UserDAO();
				String fName = txtFirstName.getText();
				String lName = txtLastName.getText();
				String email = txtEmail.getText();
				String password = MD5Util.getMd5(new String(txtPassword.getPassword()));
				String confirmPassword = MD5Util.getMd5(new String(txtConfirmPassword.getPassword()));

				try {
					// check if form is validated
					if (fName.isEmpty() || lName.isEmpty() || email.isEmpty() || password.isEmpty())
						throw new Exception("Please fill in all the fields");

					if (!password.equals(confirmPassword))
						throw new Exception("Input passwords do not match!");
					
					if(userDAO.emailExists(email))
						throw new Exception("Email already registered!");
						
					// Creates User Object
					User user = new User();
					user.setFirstName(fName);
					user.setLastName(lName);
					user.setEmail(email);
					user.setPassword(password);
					user.setType(UserType.STUDENT);
					user.setEnabled(true);

					// Attempt to insert new user into DB
					try {
						userDAO.addUser(user);
					} catch (Exception ex) {
						throw new Exception("Please contact the administrator");
					}
					
					JOptionPane.showMessageDialog(getContentPane(), "Registration Completed!\n You can Login now.", "Registration Completed",
							JOptionPane.INFORMATION_MESSAGE);
					
					backToLogin();

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(getContentPane(), ex.getMessage(), "Registration Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});

	}

	private void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 536, 364);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblRegister = new JLabel("REGISTER");
		lblRegister.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegister.setFont(new Font("Tahoma", Font.BOLD, 16));

		JSeparator separator = new JSeparator();

		btnBackToLogin = new JButton("Back to Login");

		lblFirstName = new JLabel("*First Name:");
		lblFirstName.setFont(new Font("Tahoma", Font.PLAIN, 12));

		lblLastName = new JLabel("*Last Name:");
		lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 12));

		lblEmail = new JLabel("*Email:");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));

		lblPassword = new JLabel("*Password:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));

		lblConfirmPassword = new JLabel("*Confirm Password:");
		lblConfirmPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));

		txtFirstName = new JTextField();
		txtFirstName.setColumns(10);

		txtLastName = new JTextField();
		txtLastName.setColumns(10);

		txtEmail = new JTextField();
		txtEmail.setColumns(10);

		txtPassword = new JPasswordField();

		txtConfirmPassword = new JPasswordField();

		btnRegister = new JButton("Register");
		btnRegister.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));

		btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		lblRequiredFields = new JLabel("* Required fields");
		lblRequiredFields.setFont(new Font("Tahoma", Font.BOLD, 10));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblRegister, GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblRequiredFields)
							.addContainerGap(513, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnRegister)
								.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lblConfirmPassword, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblPassword, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblEmail, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblLastName, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblFirstName, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED, 1, GroupLayout.PREFERRED_SIZE)
											.addComponent(txtFirstName, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(txtLastName, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
											.addGap(1))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
											.addGap(1))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
											.addGap(2))
										.addComponent(txtConfirmPassword, GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))))
							.addGap(295))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(separator, GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnBackToLogin)
							.addContainerGap(502, Short.MAX_VALUE))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblRegister, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtFirstName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblFirstName))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtLastName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblLastName))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblEmail))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPassword))
					.addGap(11)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblConfirmPassword)
						.addComponent(txtConfirmPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblRequiredFields))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(27)
							.addComponent(btnRegister)))
					.addGap(18)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 12, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnBackToLogin)
					.addGap(68))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
