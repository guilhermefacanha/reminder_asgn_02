package com.csis.reminder.view;

import java.awt.EventQueue;
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
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.csis.reminder.util.ScreenUtil;

public class Login extends JFrame {

	private static final long serialVersionUID = 8041032027533025878L;
	private JPanel contentPane;
	private JTextField txtEmail;
	private JPasswordField txtPassword;
	private JButton btnRegisterHere;
	private JButton btnEnter;

	private Register register = new Register(this);
	private MainWindow mainWindow = new MainWindow();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		init();
		setBounds(ScreenUtil.resizeScreen(0.5));
		createActions();
		setTitle("Reminder - Login");

	}

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

		// action for enter
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// verify login
				if (txtEmail.getText().equals("admin@email.com")
						&& new String(txtPassword.getPassword()).equals("admin")) {
					JOptionPane.showMessageDialog(getContentPane(), "Login Successful!", "",
							JOptionPane.INFORMATION_MESSAGE);
					
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

	private void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblLogin = new JLabel("LOGIN");
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 16));

		txtEmail = new JTextField();
		txtEmail.setColumns(10);

		JLabel lblEmail = new JLabel("email");

		JLabel lblPassword = new JLabel("password");

		txtPassword = new JPasswordField();

		JSeparator separator = new JSeparator();

		btnEnter = new JButton("Enter");

		btnRegisterHere = new JButton("Register Here");
		btnRegisterHere.setBackground(UIManager.getColor("info"));

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblLogin, GroupLayout.PREFERRED_SIZE, 794, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(174)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnEnter)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblEmail)
										.addComponent(lblPassword))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, 408, GroupLayout.PREFERRED_SIZE)
										.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 408, GroupLayout.PREFERRED_SIZE)))))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(separator, GroupLayout.DEFAULT_SIZE, 794, Short.MAX_VALUE)))
					.addContainerGap())
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addGap(537)
					.addComponent(btnRegisterHere)
					.addContainerGap(178, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblLogin)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblEmail, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
					.addGap(9)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPassword))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnEnter)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 9, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnRegisterHere)
					.addContainerGap(301, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
