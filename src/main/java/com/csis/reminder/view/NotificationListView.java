package com.csis.reminder.view;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;

import com.csis.reminder.dao.CourseDAO;
import com.csis.reminder.dao.NotificationDAO;
import com.csis.reminder.entity.Notification;
import com.csis.reminder.entity.User;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
	
	/**
	 * Method which sets up basic settings for the NotificationListView frame
	 */
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
	
	/**
	 * Method which loads the notifications into the table
	 */
	private void loadNotifications()	{
		dtm = (DefaultTableModel) tbNotifications.getModel();
		List<Notification> notifications = notificationDAO.getAllNotifications(user);
		// loops through list
		for (Notification notification : notifications)	{
			dtm.addRow(notification.getData());
		}
	}
	
	/**
	 * Method which sets up the event handlers
	 */
	private void setupActions()	{
		// button add notification action
		btnNewNotification.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(new CourseDAO().hasUserCourse(user)) {
					NotificationFormView form = new NotificationFormView(user, desktop);
					desktop.add(form);
					form.show();
					
					// close current window
					dispose();
				}
				else
					JOptionPane.showMessageDialog(null, "You must have at least one Course with one Event in order to create a notification.");
			}
		});
		// button edit notification action
		btnEditNotification.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selectedRow = tbNotifications.getSelectedRow();
				if (selectedRow > -1)	{
					Long id = (Long) tbNotifications.getValueAt(selectedRow, 0);
					NotificationFormView form = new NotificationFormView(id, user, desktop);
					desktop.add(form);
					form.show();
					
					// close current window
					dispose();
				}
				else	{ // if the user has not selected a notification yet
					JOptionPane.showMessageDialog(getContentPane(), "Select a row to edit!", "Error",
							JOptionPane.ERROR_MESSAGE);
				}			
			}
		});
		
		// button delete notification action
		btnDeleteNotification.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selectedRow = tbNotifications.getSelectedRow();
				if (selectedRow > -1) {
					Long id = (Long) tbNotifications.getValueAt(selectedRow, 0);
					int confirm = JOptionPane.showConfirmDialog(getContentPane(),
							"Confirm delete Notification with id " + id + " ?");
					if (confirm == 0)	{
						try	{
							notificationDAO.deleteNotification(id);
							dtm.removeRow(selectedRow);
							JOptionPane.showMessageDialog(getContentPane(), "Notification deleted", "Info",
									JOptionPane.INFORMATION_MESSAGE);
						}
						catch (Exception ex)	{
							JOptionPane.showMessageDialog(getContentPane(), ex.getMessage(), "Error",
									JOptionPane.ERROR_MESSAGE);						
						}
					}
				}
				else	{
					JOptionPane.showMessageDialog(getContentPane(), "Select a record to be deleted!", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}			
		});
	}
	
	
	/**
	 * Method responsible for instantiating GUI components
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
