package com.csis.reminder.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.SplashScreen;
import java.awt.SystemColor;
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
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.csis.reminder.dao.UserDAO;
import com.csis.reminder.util.ScreenUtil;

/**
 * @author Reminder Group
 * Class that is the entry point of the System
 * It fires the JFrame where the user can login or register into the system
 */
public class Login extends JFrame {

	private static final long serialVersionUID = 8041032027533025878L;
	private JPanel contentPane;
	
	//text field for user email input
	private JTextField txtEmail;
	//text field for user password input
	private JPasswordField txtPassword;

	private JButton btnRegisterHere;
	private JButton btnEnter;

	//Register view frame object
	private Register register = new Register(this);
	//MainWindow view frame object
	private MainWindow mainWindow;
	
	private JLabel label;
	
	//User Data Access Object
	private UserDAO userDao = new UserDAO();

	
	/**
	 * Constructor for the Login View Frame
	 */
	public Login() {
		init();
		ScreenUtil.centerWindow(this);
		createActions();
		setTitle("Reminder - Login");
	}

	/**
	 * Method that stores all button action functions
	 */
	private void createActions() {
		
		// action for button register
		btnRegisterHere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!register.isVisible()) {
					register.setVisible(true);
					setVisible(false);
				}
			}
		});

		// action for login button
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String email = txtEmail.getText();
				String password = new String(txtPassword.getPassword());
				// verify login
				if (userDao.verifyLogin(email,password)) {
					JOptionPane.showMessageDialog(getContentPane(), "Login Successful!", "",
							JOptionPane.INFORMATION_MESSAGE);
					
					mainWindow = new MainWindow();
					if (!mainWindow.isVisible()) {
						setVisible(false);
						mainWindow.setVisible(true);
					}
				} else {
					JOptionPane.showMessageDialog(getContentPane(), "Email/Password Invalid!", "Login Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	/**
	 * Method that stores all GUI automatic generated code
	 */
	private void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 467, 293);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblLogin = new JLabel("REMINDER");
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 16));

		txtEmail = new JTextField();
		txtEmail.setColumns(10);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 12));

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 12));

		txtPassword = new JPasswordField();

		JSeparator separator = new JSeparator();

		btnEnter = new JButton("Enter");
		btnEnter.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnEnter.setBackground(UIManager.getColor("InternalFrame.inactiveTitleGradient"));

		btnRegisterHere = new JButton("Register Here");
		btnRegisterHere.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnRegisterHere.setBackground(SystemColor.info);
		
		label = new JLabel("LOGIN");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.BOLD, 16));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(separator, GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)
							.addGap(10))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnRegisterHere)
							.addContainerGap(320, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblLogin, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)
								.addComponent(label, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE))
							.addGap(10))))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnEnter, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblPassword)
								.addComponent(lblEmail))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(txtPassword)
								.addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(142, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(6)
					.addComponent(lblLogin)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEmail, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(9)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPassword)
						.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(15)
					.addComponent(btnEnter)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 9, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnRegisterHere)
					.addGap(265))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
