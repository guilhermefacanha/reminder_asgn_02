package com.csis.reminder.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFormattedTextField;
//	import javax.swing.JCheckBox;
//	import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
//	import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.csis.reminder.dao.CourseDAO;
import com.csis.reminder.dao.EventDAO;
import com.csis.reminder.entity.Course;
import com.csis.reminder.entity.Event;
import com.csis.reminder.entity.User;
import com.csis.reminder.entity.enumeration.EventType;
import com.csis.reminder.util.ScreenUtil;
import java.awt.SystemColor;
import javax.swing.JSeparator;

public class EventFormView extends JInternalFrame {
	private static final long serialVersionUID = -7030310764143744275L;

	private CourseDAO coursedao = new CourseDAO();
	private EventDAO eventDAO = new EventDAO();

	private User user;
	private Event event;

	private List<Course> userCourses;

	@SuppressWarnings("unused")
	private Long courseid = null;
	private JTextField txtDate;
	private JTextField txtId;
	private JLabel lbl2;
	private JLabel lblFormTitle;
	private JButton btnSave;
	private JTextField txtEventName;
	private JTextField txtDescription;
	private JComboBox<Course> cmbCourse;
	private JComboBox<EventType> cmbEventType;

	private JDesktopPane desktop;
	private JButton btnGoBack;
	private JSeparator separator;

	/**
	 * Default Constructor
	 */
	public EventFormView() {
		init();
		config();
		createActions();
	}

	/**
	 * Create the frame.
	 * @param desktop - main window desktop object
	 */
	public EventFormView(JDesktopPane desktop) {
		this();
		this.desktop = desktop;
	}

	/**
	 * Create the frame.
	 * @param user - logged user
	 * @param desktop - main window desktop object
	 */
	public EventFormView(User user, JDesktopPane desktop) {
		this(desktop);
		this.user = user;
		this.userCourses = coursedao.getAllCourses(this.user);
		loadCombos();
	}

	/**
	 * Constructor
	 * @param courseId - course id related to event
	 * @param user - logged user
	 * @param desktop - main window desktop object
	 */
	public EventFormView(long courseId, User user, JDesktopPane desktop) {
		this(user, desktop);
		this.courseid = courseId;
		loadCourse(courseId);
	}

	private void loadCombos() {
		for (Course course : userCourses) {
			cmbCourse.addItem(course);
		}

		for (EventType type : EventType.values()) {
			cmbEventType.addItem(type);
		}
	}

	/**
	 * Load the courses for an event
	 * @param eventId - event id
	 */
	private void loadCourse(long eventId) {
		event = eventDAO.getEvent(eventId);

		if (event != null) {
			txtId.setVisible(true);
			lbl2.setVisible(true);
			lblFormTitle.setText("Edit Event");
			btnSave.setText("Edit");

			txtId.setText(event.getId() + "");
			txtEventName.setText(event.getEventName());
			txtDate.setText(event.getDateStr());
			txtDescription.setText(event.getDescription());
			cmbCourse.setSelectedItem(event.getCourse());
			cmbEventType.setSelectedItem(event.getType());
		}

	}

	private void config() {
		setTitle("Event Form");
		setClosable(true);
		setMaximizable(true);
		setResizable(true);

	}

	/**
	 * Method to redirect the window to list view
	 */
	private void goToListView() {
		EventListView eventListView = new EventListView(desktop, user);
		desktop.add(eventListView);
		eventListView.show();

		// close current window
		dispose();
	}

	private void createActions() {

		// btn goback action
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToListView();
			}
		});

		// btn save course action
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					boolean edit = false;
					validateForm();

					// course in edit mode
					if (event != null && event.getId() > 0) {
						edit = true;
					} else {
						event = new Event();
					}

					DateFormat formatter = new SimpleDateFormat(ScreenUtil.DATE_TIME_FORMAT);
					event.setDate(formatter.parse(txtDate.getText()));

					event.setEventName(txtEventName.getText());
					event.setDescription(txtDescription.getText());
					event.setCourse((Course) cmbCourse.getSelectedItem());
					event.setType((EventType) cmbEventType.getSelectedItem());
					eventDAO.save(event);

					JOptionPane.showMessageDialog(getContentPane(), "Event " + (edit ? "Edited!" : "Added!"), "Info",
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
		if (txtEventName.getText().isEmpty())
			throw new Exception("Please fill Event Name");
		if (txtDate.getText().isEmpty())
			throw new Exception("Please fill Event Date");
		else {
			if (!ScreenUtil.isDateValid(txtDate.getText(), ScreenUtil.DATE_TIME_FORMAT))
				throw new Exception("Invalid input Date: " + txtDate.getText());
		}

	}

	private void init() {

		setBounds(100, 100, 346, 351);

		btnSave = new JButton("Save");
		btnSave.setBounds(170, 243, 150, 20);
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSave.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));

		lblFormTitle = new JLabel("New Event");
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

		JLabel lblEventType = new JLabel("*Event Type:");
		lblEventType.setBounds(10, 100, 108, 20);
		lblEventType.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().add(lblEventType);
		// getContentPane().add(label_2);

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

		cmbEventType = new JComboBox();
		cmbEventType.setBounds(170, 101, 150, 20);
		getContentPane().add(cmbEventType);

		JLabel lbleventName = new JLabel("*Event Name:");
		lbleventName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbleventName.setBounds(10, 160, 108, 20);
		getContentPane().add(lbleventName);

		JLabel lblEventName = new JLabel("Description:");
		lblEventName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEventName.setBounds(10, 191, 108, 20);
		getContentPane().add(lblEventName);

		txtEventName = new JTextField();
		txtEventName.setBounds(170, 161, 150, 20);
		getContentPane().add(txtEventName);
		txtEventName.setColumns(10);

		txtDescription = new JTextField();
		txtDescription.setBounds(170, 192, 150, 20);
		getContentPane().add(txtDescription);
		txtDescription.setColumns(10);

		btnGoBack = new JButton("Go Back to List");

		btnGoBack.setBackground(SystemColor.info);
		btnGoBack.setBounds(10, 282, 150, 23);
		getContentPane().add(btnGoBack);

		separator = new JSeparator();
		separator.setBounds(10, 274, 310, 9);
		getContentPane().add(separator);
	}
}
