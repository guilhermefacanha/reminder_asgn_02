package com.csis.reminder.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.csis.reminder.dao.*;
import com.csis.reminder.entity.*;
import com.csis.reminder.entity.enumeration.EventType;
import com.csis.reminder.util.ScreenUtil;
import java.awt.SystemColor;
import javax.swing.JSeparator;


public class NotificationFormView extends JInternalFrame
{
	private static final long serialVersionUID = -7030310764143744275L;

	private CourseDAO courseDAO = new CourseDAO();
	private EventDAO eventDAO = new EventDAO();
	private NotificationDAO notificationDAO = new NotificationDAO();

	private User user;
	private Course course;
	private Event event;
	private Notification notification;

	private List<Course> userCourses;
	private List<Event> events;

	@SuppressWarnings("unused")
	private Long notificationid = null;
	private JTextField txtDate;
	private JTextField txtId;
	private JLabel lbl2;
	private JLabel lblFormTitle;
	private JButton btnSave;
	private JTextField txtNotificationName;
	
	private JComboBox<Course> cmbCourse;
	private JComboBox<Event> cmbEvent;

	private JDesktopPane desktop;
	private JButton btnGoBack;
	private JSeparator separator;

	/**
	 * Default Constructor
	 */
	public NotificationFormView() {
		init();
		config();
		createActions();
	}

	/**
	 * Create the frame.
	 * @param desktop - main window desktop object
	 */
	public NotificationFormView(JDesktopPane desktop) {
		this();
		this.desktop = desktop;
	}

	/**
	 * Create the frame.
	 * @param user - logged user
	 * @param desktop - main window desktop object
	 */
	public NotificationFormView(User user, JDesktopPane desktop) {
		this(desktop);
		this.user = user;
		this.userCourses = courseDAO.getAllCourses(this.user);
		loadComboCourse();
		
		
	}

	/**
	 * Constructor
	 * @param notificationId - notification related to event 
	 * @param user - logged user
	 * @param desktop - main window desktop object
	 */
	public NotificationFormView(long notificationId, User user, JDesktopPane desktop) {
		this(user, desktop);
		this.notificationid = notificationId;
		loadNotification(notificationId);
	}

	private void loadComboCourse() {
		for (Course course : userCourses) {
			cmbCourse.addItem(course);
		}

	}

	private void loadEvents(Course course) {
		this.events = eventDAO.getAllEventsByCourse(course);
		// checks if there are events for a that course, if not, removes that course from the cmbCourse
		if (events.isEmpty())	{
			cmbCourse.removeItem(course);
			loadEvents((Course)cmbCourse.getSelectedItem());
		}
		else	{
		loadComboEvent();
		}
	}
	
	private void loadComboEvent() {
		cmbEvent.removeAllItems();
		for (Event event : events) {
			cmbEvent.addItem(event);
			System.out.println(" item " + event.getEventName());
		}
		

	}
	/**
	 * Load the courses for an event
	 * @param eventId - event id
	 */
	private void loadNotification(long notificationId) {
		notification = notificationDAO.getNotification(notificationId);

		if (notification != null) {
			txtId.setVisible(true);
			lbl2.setVisible(true);
			lblFormTitle.setText("Edit notification");
			btnSave.setText("Edit");

			txtId.setText(notification.getId() + "");
			txtNotificationName.setText(notification.getNotificationName());
			txtDate.setText(notification.getDateStr());
			cmbEvent.setSelectedItem(notification.getEvent());
		}

	}

	private void config() {
		setTitle("Notification Form");
		setClosable(true);
		setMaximizable(true);
		setResizable(true);

	}

	

	private void createActions() {
	
		// comboBox cmbCourse action 
		cmbCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				loadEvents((Course) cmbCourse.getSelectedItem());
				System.out.println(" entrei course ");
			}
		});
		
		
		// btnSave notification action
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				try {
					boolean edit = false;
					validateForm();

					// notification in edit mode
					if (notification != null && notification.getId() > 0) 
					{
						edit = true;					   
					} 

					if(notification == null)
						notification = new Notification();
					
					DateFormat formatter = new SimpleDateFormat(ScreenUtil.DATE_TIME_FORMAT);
						
			    	notification.setDate(formatter.parse(txtDate.getText()));
			    	notification.setNotificationName(txtNotificationName.getText());
	
			    	notification.setEvent((Event) cmbEvent.getSelectedItem());
			    	notification.setChecked(false);
					
					notificationDAO.save(notification);

				  	JOptionPane.showMessageDialog(getContentPane(), "Notification " + (edit ? "Edited!" : "Added!"), "Info",
							JOptionPane.INFORMATION_MESSAGE);

					goToListView();
					
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(getContentPane(), e2.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		// btnGoBack action
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				NotificationListView form = new NotificationListView(user, desktop);
				desktop.add(form);
				form.show();
				
				// close current window
				dispose();
				
				
			}
		});
	}
	
	
	/**
	 * Method to redirect the window to list view
	 */
	private void goToListView() {
		NotificationListView notificationListView = new NotificationListView(user, desktop);
		desktop.add(notificationListView);
		notificationListView.show();

		// close current window
		dispose();
	}
	
	
	
	/**
	 * Method which verifies if the user provided all the required inputs and if 
	 * they are valid.
	 * @throws Exception - a specific exception depending on which input contains an error
	 */
	private void validateForm() throws Exception {
		
		// check if form is validated
		if (txtNotificationName.getText().isEmpty())
			throw new Exception("Please fill notification Name");
		if (txtDate.getText().isEmpty())
			throw new Exception("Please fill notification Date");
		if (cmbCourse.getSelectedIndex() == -1)
			throw new Exception("Please select Course");
		if (cmbEvent.getSelectedIndex() == -1)
			throw new Exception("Please select Event");
		else {
			if (!ScreenUtil.isDateValid(txtDate.getText(), ScreenUtil.DATE_TIME_FORMAT))
				throw new Exception("Invalid input Date: " + txtDate.getText());
			if (ScreenUtil.isDatePassed(txtDate.getText(), ScreenUtil.DATE_TIME_FORMAT))
				throw new Exception("Date already passed: " + txtDate.getText());
			// Gets selected event to validate the notification 
			event = (Event) cmbEvent.getSelectedItem();
			if (ScreenUtil.isNotificationDatePastEvent(txtDate.getText(), ScreenUtil.DATE_TIME_FORMAT, event))
				throw new Exception("Date for notification must be prior to the event's date!");
		}
	}

	private void init() {

		setBounds(100, 100, 346, 351);

		btnSave = new JButton("Save");
		btnSave.setBounds(170, 243, 150, 20);
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSave.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));

		lblFormTitle = new JLabel("New Notification");
		lblFormTitle.setBounds(10, 4, 119, 27);
		lblFormTitle.setFont(new Font("Tahoma", Font.BOLD, 16));

		lbl2 = new JLabel(" Id:");
		lbl2.setBounds(10, 39, 108, 20);
		lbl2.setVisible(false);
		lbl2.setFont(new Font("Tahoma", Font.PLAIN, 12));

		txtId = new JTextField();
		txtId.setBounds(170, 39, 150, 20);
		txtId.setVisible(false);
		txtId.setEditable(false);
		txtId.setColumns(10);

		getContentPane().setLayout(null);
		getContentPane().add(lblFormTitle);

		JLabel label_6 = new JLabel("");
		label_6.setBounds(150, 4, 150, 35);
		getContentPane().add(label_6);
		getContentPane().add(lbl2);
		getContentPane().add(txtId);

		JLabel lblcourse = new JLabel("*Course:");
		lblcourse.setBounds(10, 70, 108, 20);
		lblcourse.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().add(lblcourse);

		JLabel lblEvent = new JLabel("*EventName:");
		lblEvent.setBounds(10, 100, 108, 20);
		lblEvent.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().add(lblEvent);
	

		JLabel lblStartDatemmddyyyy = new JLabel("*Date (MM/dd/yyyy HH:mm)");
		lblStartDatemmddyyyy.setBounds(10, 130, 156, 20);
		lblStartDatemmddyyyy.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().add(lblStartDatemmddyyyy);

		txtDate = new JFormattedTextField(ScreenUtil.createFormatter("##/##/#### ##:##"));
		txtDate.setBounds(170, 130, 150, 20);
		txtDate.setColumns(10);
		getContentPane().add(txtDate);

		JLabel label_4 = new JLabel("* Required fields");
		label_4.setBounds(10, 222, 108, 20);
		label_4.setFont(new Font("Tahoma", Font.BOLD, 10));
		getContentPane().add(label_4);

		JLabel label_8 = new JLabel("");
		label_8.setBounds(0, 354, 150, 35);
		getContentPane().add(label_8);
		getContentPane().add(btnSave);

		cmbCourse = new JComboBox();
		cmbCourse.setBounds(170, 70, 150, 20);
		getContentPane().add(cmbCourse);

		cmbEvent = new JComboBox();
		cmbEvent.setBounds(170, 101, 150, 20);
		getContentPane().add(cmbEvent);

		JLabel lblNotificationName = new JLabel("*Notification Name:");
		lblNotificationName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNotificationName.setBounds(10, 160, 108, 20);
		getContentPane().add(lblNotificationName);

		txtNotificationName = new JTextField();
		txtNotificationName.setBounds(170, 161, 150, 20);
		getContentPane().add(txtNotificationName);
		txtNotificationName.setColumns(10);
		
		btnGoBack = new JButton("Go Back to List");
		
		btnGoBack.setBackground(SystemColor.info);
		btnGoBack.setBounds(10, 282, 150, 23);
		getContentPane().add(btnGoBack);

		separator = new JSeparator();
		separator.setBounds(10, 274, 310, 9);
		getContentPane().add(separator);
		
	}
	
	
}
