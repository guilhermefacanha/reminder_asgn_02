package com.csis.reminder.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.csis.reminder.entity.User;
import com.csis.reminder.entity.enumeration.UserType;
import com.csis.reminder.session.UserSession;
import com.csis.reminder.util.ScreenUtil;
import javax.swing.JLabel;
import javax.swing.UIManager;
import java.awt.Font;

/**
 * @author Reminder Group Class responsible for the main layout of the system
 *         with MENU and call methods to Internal Frames
 */
public class MainWindow extends JFrame {

	private static final long serialVersionUID = 6433406218504113020L;
	private JPanel contentPane;
	private JMenuItem mntmExit;
	private JMenuItem mntmListUsers;
	private JMenuItem mntmNewUser;

	private JLabel lblWelcome;
	private JMenu mnUsers;

	/**
	 * Constructor to create the frame and call initialization methods
	 */
	public MainWindow() {
		init();
		ScreenUtil.centerWindow(this);
		setTitle("Reminder System v1.0");
		setBounds(ScreenUtil.resizeScreen(0.8));
		createWelcomeLabel();
		createActions();
		setMenuPermissions();
	}

	/**
	 * Method to apply permission to MENU with logged user type attribute
	 */
	private void setMenuPermissions() {
		User user = UserSession.getUser();
		if(user!=null && user.getType().equals(UserType.ADMIN)) {
			mnUsers.setVisible(true);
		}
	}

	private void createWelcomeLabel() {
		User user = UserSession.getUser();
		if (user != null) {
			lblWelcome.setText(lblWelcome.getText() + " " + user.getFirstName() + " " + user.getLastName());
		}
	}

	/**
	 * Method that stores all button action functions
	 */
	private void createActions() {

		// menu item exit action
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}

	/**
	 * Method that stores all GUI automatic generated code
	 */
	private void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		JMenuBar menu = new JMenuBar();
		setJMenuBar(menu);

		mnUsers = new JMenu("Users");
		mnUsers.setVisible(false);
		mnUsers.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
		menu.add(mnUsers);

		mntmListUsers = new JMenuItem("List Users");
		mnUsers.add(mntmListUsers);

		mntmNewUser = new JMenuItem("New User");
		mnUsers.add(mntmNewUser);

		JMenu mnExit = new JMenu("Exit");
		mnExit.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
		menu.add(mnExit);

		mntmExit = new JMenuItem("Exit");

		mnExit.add(mntmExit);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
				lblWelcome = new JLabel("Welcome, ");
				lblWelcome.setFont(new Font("Tahoma", Font.BOLD, 14));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblWelcome, GroupLayout.PREFERRED_SIZE, 706, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(1306, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblWelcome)
					.addContainerGap(766, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
