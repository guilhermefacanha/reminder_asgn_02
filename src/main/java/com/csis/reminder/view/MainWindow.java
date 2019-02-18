package com.csis.reminder.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.csis.reminder.dao.UserDAO;
import com.csis.reminder.entity.User;
import com.csis.reminder.entity.enumeration.UserType;
import com.csis.reminder.session.UserSession;
import com.csis.reminder.util.ScreenUtil;
import javax.swing.JLabel;
import javax.swing.UIManager;
import java.awt.Font;

import javax.swing.JList;

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
	private JList lstUsers;
	
	private DefaultListModel dlm = new DefaultListModel();
	private JMenu mnCourses;
	private JMenuItem mntnListCourses;
	private JMenuItem mntnAddCourses;
	private JMenu mnEvents;
	private JMenuItem mntnListEvents;
	private JMenuItem mntnAddEvent;

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
		
		// menu item for list users
		mntmListUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lstUsers.setVisible(true);	
				UserDAO userdao = new UserDAO();
				List<User> users = userdao.getAllUsers();
				String fmt = "%-20s %-40s %-40s %-80s";
				dlm.addElement(String.format(fmt, "User ID", "First Name", "Last Name", "Email"));
				for (User u: users)	{
					dlm.addElement(String.format(fmt, u.getId(), u.getFirstName(), u.getLastName(), u.getEmail()));
				}
				lstUsers.setModel(dlm);					
			}			
		});
		
		// menu item add user action
		mnUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
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
		
		mnCourses = new JMenu("Couses");
		menu.add(mnCourses);
		
		mntnListCourses = new JMenuItem("List Couses");
		mnCourses.add(mntnListCourses);
		
		mntnAddCourses = new JMenuItem("Add a Course");
		mnCourses.add(mntnAddCourses);
		
		mnEvents = new JMenu("Events");
		menu.add(mnEvents);
		
		mntnListEvents = new JMenuItem("List Events");
		mnEvents.add(mntnListEvents);
		
		mntnAddEvent = new JMenuItem("Add an Event");
		mnEvents.add(mntnAddEvent);

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
		
		lstUsers = new JList();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblWelcome, GroupLayout.PREFERRED_SIZE, 706, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(39)
							.addComponent(lstUsers, GroupLayout.PREFERRED_SIZE, 916, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(555, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblWelcome)
					.addGap(69)
					.addComponent(lstUsers, GroupLayout.PREFERRED_SIZE, 549, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(148, Short.MAX_VALUE))
		);
		
		contentPane.setLayout(gl_contentPane); // Adds the table to the content panel
		lstUsers.setModel(dlm);
		lstUsers.setVisible(false);
		
				
		
		
	}
}
