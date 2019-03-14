package com.csis.reminder.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.csis.reminder.dao.UserDAO;
import com.csis.reminder.entity.User;
import com.csis.reminder.entity.enumeration.UserType;
import com.csis.reminder.util.MD5Util;
import java.awt.SystemColor;
import javax.swing.JSeparator;

public class UserFormView extends JInternalFrame {

	private static final long serialVersionUID = -7030310764143744275L;

	private UserDAO userdao = new UserDAO();

	private User user;
	private JTextField txtFname;
	private JTextField txtLname;
	private JTextField txtEmail;
	private JTextField txtId;
	private JLabel lbl2;
	private JPasswordField txtPass;
	private JPasswordField txtPassConf;
	private JComboBox<UserType> userTypeCmb;
	private JCheckBox chkEnable;
	private JLabel lblFormTitle;
	private JButton btnSave;
	private JLabel lblPass;
	private JLabel lblPassConf;

	private JDesktopPane desktop;
	private JButton btnGoBack;
	
	/**
	 * Default Constructor
	 */
	public UserFormView() {
	}

	/**
	 * Create the frame.
	 * @param desktop 
	 */
	public UserFormView(JDesktopPane desktop) {
		this.desktop = desktop;
		init();
		config();
		createActions();
	}

	/**
	 * @param userId
	 *            - user id to be loaded and start the edit form
	 */
	public UserFormView(long userId, JDesktopPane desktop) {
		this(desktop);
		loadUser(userId);
	}

	private void loadUser(long userId) {
		user = userdao.getUser(userId);
		if (user != null) {
			txtId.setVisible(true);
			lbl2.setVisible(true);
			lblFormTitle.setText("Edit User");
			btnSave.setText("Edit");
			lblPass.setText("Change Password");

			txtId.setText(user.getId() + "");
			txtEmail.setText(user.getEmail());
			txtFname.setText(user.getFirstName());
			txtLname.setText(user.getLastName());
			chkEnable.setSelected(user.isEnabled());
			userTypeCmb.setSelectedItem(user.getType());
		}
	}

	private void config() {
		setTitle("User Form");
		setClosable(true);
		setMaximizable(true);
		setResizable(true);

		userTypeCmb.addItem(UserType.STUDENT);
		userTypeCmb.addItem(UserType.ADMIN);
	}

	private void goToListView() {
		UserListView listUserView = new UserListView(desktop);
		desktop.add(listUserView);
		listUserView.show();
		
		// close current window
		dispose();
	}

	private void createActions() {
		
		//btnGoBack Action
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				goToListView();
			}
		});

		// btn save user action
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					boolean edit = false;
					validateForm();
					String password = MD5Util.getMd5(new String(txtPass.getPassword()));
					String confirmPassword = MD5Util.getMd5(new String(txtPassConf.getPassword()));

					// user in edit mode
					if (user != null && user.getId() > 0) {
						edit = true;

						// change password
						if (!password.isEmpty()) {
							if (!password.equals(confirmPassword))
								throw new Exception("Input passwords do not match!");

							user.setPassword(password);
						}
					}
					// new user mode
					else {

						// checks if the 2 passwords input match
						if (password.isEmpty())
							throw new Exception("Required field Password!");

						// checks if the 2 passwords input match
						if (!password.equals(confirmPassword))
							throw new Exception("Input passwords do not match!");

						// checks if email is already registered
						if (userdao.emailExists(txtEmail.getText()))
							throw new Exception("Email already registered!");

						user = new User();
						user.setPassword(password);
					}

					user.setEmail(txtEmail.getText());
					user.setFirstName(txtFname.getText());
					user.setLastName(txtLname.getText());
					user.setEnabled(chkEnable.isSelected());
					user.setType((UserType) userTypeCmb.getSelectedItem());

					userdao.saveUser(user);

					JOptionPane.showMessageDialog(getContentPane(), "User " + (edit ? "Edited!" : "Added!"), "Info",
							JOptionPane.INFORMATION_MESSAGE);
					
					goToListView();

				} catch (Exception e2) {
					JOptionPane.showMessageDialog(getContentPane(), e2.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	private void validateForm() throws Exception {

		// check if form is validated
		if (txtFname.getText().isEmpty() || txtLname.getText().isEmpty() || txtEmail.getText().isEmpty())
			throw new Exception("Please fill in all the fields");

	}

	private void init() {
		setBounds(100, 100, 317, 423);

		txtEmail = new JTextField();
		txtEmail.setBounds(118, 132, 150, 20);
		txtEmail.setColumns(10);

		JLabel label_2 = new JLabel("*Email:");
		label_2.setBounds(10, 132, 108, 20);
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 12));

		btnSave = new JButton("Save");
		btnSave.setBounds(118, 311, 150, 20);
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSave.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));

		lblFormTitle = new JLabel("New User");
		lblFormTitle.setBounds(10, 4, 119, 27);
		lblFormTitle.setFont(new Font("Tahoma", Font.BOLD, 16));

		lbl2 = new JLabel(" Id:");
		lbl2.setBounds(10, 39, 108, 20);
		lbl2.setVisible(false);
		lbl2.setFont(new Font("Tahoma", Font.PLAIN, 12));

		txtId = new JTextField();
		txtId.setBounds(118, 39, 150, 20);
		txtId.setVisible(false);
		txtId.setEditable(false);
		txtId.setColumns(10);

		lblPassConf = new JLabel("*Confirm Password:");
		lblPassConf.setBounds(10, 256, 108, 20);
		lblPassConf.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().setLayout(null);
		getContentPane().add(lblFormTitle);

		JLabel label_6 = new JLabel("");
		label_6.setBounds(150, 4, 150, 35);
		getContentPane().add(label_6);
		getContentPane().add(lbl2);
		getContentPane().add(txtId);

		JLabel label = new JLabel("*First Name:");
		label.setBounds(10, 70, 108, 20);
		label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().add(label);

		txtFname = new JTextField();
		txtFname.setBounds(118, 70, 150, 20);
		txtFname.setColumns(10);
		getContentPane().add(txtFname);

		JLabel label_1 = new JLabel("*Last Name:");
		label_1.setBounds(10, 101, 108, 20);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().add(label_1);

		txtLname = new JTextField();
		txtLname.setBounds(118, 101, 150, 20);
		txtLname.setColumns(10);
		getContentPane().add(txtLname);
		getContentPane().add(label_2);
		getContentPane().add(txtEmail);

		JLabel lbltype = new JLabel("*Type:");
		lbltype.setBounds(10, 163, 108, 20);
		lbltype.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().add(lbltype);

		userTypeCmb = new JComboBox();
		userTypeCmb.setBounds(118, 163, 150, 20);
		getContentPane().add(userTypeCmb);

		JLabel lblenabled = new JLabel("*Enabled:");
		lblenabled.setBounds(10, 194, 108, 20);
		lblenabled.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().add(lblenabled);

		chkEnable = new JCheckBox("");
		chkEnable.setBounds(118, 194, 150, 20);
		chkEnable.setSelected(true);
		getContentPane().add(chkEnable);

		lblPass = new JLabel("*Password:");
		lblPass.setBounds(10, 225, 108, 20);
		lblPass.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().add(lblPass);

		txtPass = new JPasswordField();
		txtPass.setBounds(118, 225, 150, 20);
		getContentPane().add(txtPass);
		getContentPane().add(lblPassConf);

		txtPassConf = new JPasswordField();
		txtPassConf.setBounds(118, 256, 150, 20);
		getContentPane().add(txtPassConf);

		JLabel label_3 = new JLabel("* Required fields");
		label_3.setBounds(10, 287, 108, 20);
		label_3.setFont(new Font("Tahoma", Font.BOLD, 10));
		getContentPane().add(label_3);

		JLabel label_8 = new JLabel("");
		label_8.setBounds(0, 354, 150, 35);
		getContentPane().add(label_8);
		getContentPane().add(btnSave);
		
		btnGoBack = new JButton("Go Back to List");
		btnGoBack.setBackground(SystemColor.info);
		
		btnGoBack.setBounds(10, 354, 150, 23);
		getContentPane().add(btnGoBack);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 346, 281, 9);
		getContentPane().add(separator);
	}
}
