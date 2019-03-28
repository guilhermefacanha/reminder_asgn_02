package com.csis.reminder.view;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import com.csis.reminder.dao.NotificationDAO;
import com.csis.reminder.entity.Notification;
import com.csis.reminder.entity.User;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class NotificationListView extends JInternalFrame {

	private static final long serialVersionUID = -5005775842044227858L;	
	
	private NotificationDAO notificationDAO = new NotificationDAO();
	private JDesktopPane desktop;
	private User user;
	private JButton btnNewNotification;
	private JButton btnEditNotification;
	private JButton btnDeleteNotification;
	private JScrollPane scrollPane;
	private JTable tbNotifications;
	private DefaultTableModel dtm;
	
	/**
	 * Create the frame.
	 */
	public NotificationListView( User user, JDesktopPane desktop) {
		this.desktop = desktop;
		this.user = user;
		
		
		init();
		config();
		loadNotifications();
		setupActions();

	}
	
	private void config() {
		setTitle("Notification List");
		setClosable(true);
		setMaximizable(true);
		setResizable(true);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		tbNotifications.setDefaultRenderer(String.class, centerRenderer);
		tbNotifications.setDefaultRenderer(Long.class, centerRenderer);
	}
	
	private void loadNotifications()	{
		dtm = (DefaultTableModel) tbNotifications.getModel();
		List<Notification> notifications = notificationDAO.getAllNotifications(user);
		// loops through list
		for (Notification notification : notifications)	{
			dtm.addRow(notification.getData());
		}
	}
	
	private void setupActions()	{
		
	}
	
	/*
	/**
	 * Launch the application.
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NotificationListView frame = new NotificationListView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/

	
	
	public void init() {
		setBounds(100, 100, 708, 367);
		
		btnNewNotification = new JButton("New");
		
		btnEditNotification = new JButton("Edit");
		
		btnDeleteNotification = new JButton("Delete");
		
		scrollPane = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnNewNotification)
							.addGap(18)
							.addComponent(btnEditNotification)
							.addGap(18)
							.addComponent(btnDeleteNotification))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 671, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(11, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewNotification)
						.addComponent(btnEditNotification)
						.addComponent(btnDeleteNotification))
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 275, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		tbNotifications = new JTable();
		tbNotifications.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id", "Course", "Event Name", "Date", "Notification Name"
			}
		));
		scrollPane.setViewportView(tbNotifications);
		getContentPane().setLayout(groupLayout);
	}
}
