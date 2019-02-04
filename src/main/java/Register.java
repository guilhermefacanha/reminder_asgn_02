import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.csis.reminder.dao.UserDAO;
import com.csis.reminder.entity.User;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Register extends JFrame {

	private JPanel contentPane;
	private JLabel lblFName;
	private JLabel lblLName;
	private JLabel lblEmail;
	private JLabel lblPassword;
	private JTextField txtFName;
	private JTextField txtLName;
	private JTextField txtEmail;
	private JPasswordField txtPassword;
	private JButton btnRegister;
	private JLabel lblTitle;

	
	UserDAO userDAO = new UserDAO();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register frame = new Register();
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
	
	public Register() {
		setupComponents();
		setupActions();
			
	}
	
	
	
	public void setupActions()	{
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
								
				String fName = txtFName.getText();
				String lName = txtLName.getText();
				String email = txtEmail.getText();
				String password = txtPassword.getText(); // Will be later replaced by getPassword method
				
				if ((((fName.isEmpty() || lName.isEmpty() || email.isEmpty() || password.isEmpty()))))	{
					JOptionPane.showMessageDialog(null, "Please fill in all the fields", "User Registration Error", JOptionPane.ERROR_MESSAGE);
				}
				// User inputs all fields
				else	{
					
					User user = new User();
					user.setFirstName(fName);
					user.setLastName(lName);
					user.setEmail(email);
					user.setPassword(password);
					
					// Attempt to create new user
					try	{
						userDAO.addUser(user);
						
					}
					catch (Exception ex) {
						System.out.println(ex.getMessage());
						ex.printStackTrace();
					}
				}
				
			}
		});
	}

	public void setupComponents()	{
		setTitle("User Registration Form");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		lblFName = new JLabel("First name: ");
		lblFName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		lblLName = new JLabel("Last name:");
		lblLName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		txtFName = new JTextField();
		txtFName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtFName.setColumns(10);
		
		txtLName = new JTextField();
		txtLName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtLName.setColumns(10);
		
		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtEmail.setColumns(10);
		
		txtPassword = new JPasswordField();
		txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		btnRegister = new JButton("Submit");
		
		btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		lblTitle = new JLabel("User Registration");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(163)
					.addComponent(lblTitle)
					.addContainerGap(105, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addGap(114)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblLName)
						.addComponent(lblFName)
						.addComponent(lblEmail, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblPassword))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(txtLName, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtFName, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE))
					.addGap(93))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(173)
					.addComponent(btnRegister, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(150, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(8)
					.addComponent(lblTitle)
					.addGap(17)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtFName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblFName))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtLName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblLName))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblEmail))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPassword))
					.addGap(18)
					.addComponent(btnRegister)
					.addContainerGap(28, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		
	}
	

}
