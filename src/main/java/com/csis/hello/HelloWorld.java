package com.csis.hello;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.persistence.EntityManager;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.csis.reminder.dao.UserDAO;
import com.csis.reminder.dao.resources.Resources;
import com.csis.reminder.entity.User;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 * Hello World Application Reminder Group - Guilherme Facanha #300294067 - João
 * Vitor Wilke Silva #300278748 - Marla Vigario #300296166
 * 
 */
public class HelloWorld extends JFrame {

	private static final long serialVersionUID = 3751257714239101844L;
	private JPanel contentPane;
	private JButton btnHello;
	private JTextArea txtDbResult;
	private JButton btnTestDb;

	UserDAO userDAO = new UserDAO();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HelloWorld frame = new HelloWorld();
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
	public HelloWorld() {
		setupComponents();
		setupAction();
	}

	private void setupComponents() {
		setTitle("Hello World Application");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		btnHello = new JButton("Hello");

		btnTestDb = new JButton("Test Database");

		JScrollPane scrollPane = new JScrollPane();

		txtDbResult = new JTextArea();
		scrollPane.setViewportView(txtDbResult);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(btnHello, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnTestDb, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
						.addGap(136))
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnTestDb, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnHello, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE).addGap(4)));
		contentPane.setLayout(gl_contentPane);
	}

	private void setupAction() {

		// Action for Button Hello
		btnHello.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(getContentPane(), "Hello World Application");
			}
		});

		// Action for Test Database
		btnTestDb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					List<User> users = userDAO.getAllUsers();

					StringBuffer str = new StringBuffer("=======    USERS FROM DATABASE    =======");
					String format = "%-20s %-40s %30s ";
					str.append("\n");
					str.append(String.format(format, "ID", "NAME", "EMAIL"));
					str.append("\n");
					for (User user : users) {
						String name = user.getFirstName() + " " + user.getLastName();
						str.append(String.format(format, user.getId(), name, user.getEmail()));
						str.append("\n");
					}

					txtDbResult.setText(str.toString());

				} catch (Exception e) {
					txtDbResult.setText("ERROR: " + e.getMessage());
					e.printStackTrace();
				}
			}
		});
	}
}
