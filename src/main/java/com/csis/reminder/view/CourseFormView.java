package com.csis.reminder.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
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
import com.csis.reminder.entity.Course;
import com.csis.reminder.entity.User;
import com.csis.reminder.util.ScreenUtil;
import java.awt.SystemColor;
import javax.swing.JSeparator;

public class CourseFormView extends JInternalFrame {
	private static final long serialVersionUID = -7030310764143744275L;

	private CourseDAO coursedao = new CourseDAO();

	private User user;
	private Course course;
	@SuppressWarnings("unused")
	private Long courseid = null;
	private JTextField txtCourseName;
	private JTextField txtInstructor;
	private JTextField txtSdate;
	private JTextField txtEdate;
	private JTextField txtId;
	private JLabel lbl2;
	private JLabel lblFormTitle;
	private JButton btnSave;

	private JDesktopPane desktop;
	private JButton btnGoBack;
	private JSeparator separator;

	/**
	 * Default Constructor
	 */
	public CourseFormView() {
		init();
		config();
		createActions();

	}

	/**
	 * Create the frame.
	 * @param desktop - main window desktop object
	 */
	public CourseFormView(JDesktopPane desktop) {
		this();
		this.desktop = desktop;
	}

	/**
	 * Create the frame.
	 * @param user - logged user
	 * @param desktop - main window desktop object
	 */
	public CourseFormView(User user, JDesktopPane desktop) {
		this(desktop);
		this.user = user;
	}

	/**
	 * Create the frame.
	 * @param courseId - course id related to event
	 * @param user - logged user
	 * @param desktop - main window desktop object
	 */
	public CourseFormView(long courseId, User user, JDesktopPane desktop) {
		this(user, desktop);
		this.courseid = courseId;
		loadCourse(courseId);
	}

	private void loadCourse(long courseid) {
		course = coursedao.getCourse(courseid);
		if (course != null) {
			txtId.setVisible(true);
			lbl2.setVisible(true);
			lblFormTitle.setText("Edit course");
			btnSave.setText("Edit");

			txtId.setText(course.getId() + "");
			txtCourseName.setText(course.getCourseName());
			txtInstructor.setText(course.getCourseInstructor());
			txtSdate.setText(course.getStartDateStr());
			txtEdate.setText(course.getEndDateStr());
		}

	}

	private void config() {
		setTitle("Course Form");
		setClosable(true);
		setMaximizable(true);
		setResizable(true);

	}

	/**
	 * Method to redirect the window to list view
	 */
	private void goToListView() {
		CourseListView courseListView = new CourseListView(desktop, user);
		desktop.add(courseListView);
		courseListView.show();

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
					if (course != null && course.getId() > 0) {
						edit = true;
					}

					DateFormat formatter = new SimpleDateFormat(ScreenUtil.DATE_FORMAT);

					if(course == null)
						course = new Course();
					
					course.setUser(user);
					course.setCourseName(txtCourseName.getText());
					if (!txtInstructor.getText().isEmpty())
						course.setCourseInstructor(txtInstructor.getText());

					if (!txtSdate.getText().isEmpty())
						course.setStartDate(formatter.parse(txtSdate.getText()));

					if (!txtEdate.getText().isEmpty())
						course.setEndDate(formatter.parse(txtEdate.getText()));

					coursedao.saveCourse(course);

					JOptionPane.showMessageDialog(getContentPane(), "Course " + (edit ? "Edited!" : "Added!"), "Info",
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
		if (txtCourseName.getText().isEmpty())
			throw new Exception("Please fill Course Name");
		if(!txtSdate.getText().isEmpty() && !ScreenUtil.isDateValid(txtSdate.getText(), ScreenUtil.DATE_FORMAT))
			throw new Exception("Invalid input for Start Date: "+txtSdate.getText());
		if(!txtEdate.getText().isEmpty() && !ScreenUtil.isDateValid(txtEdate.getText(), ScreenUtil.DATE_FORMAT))
			throw new Exception("Invalid input for End Date: "+txtEdate.getText());

	}

	private void init() {

		setBounds(100, 100, 323, 324);

		btnSave = new JButton("Save");
		btnSave.setBounds(118, 215, 150, 20);
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSave.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));

		lblFormTitle = new JLabel("New Course");
		lblFormTitle.setBounds(10, 4, 119, 27);
		lblFormTitle.setFont(new Font("Tahoma", Font.BOLD, 16));

		lbl2 = new JLabel(" Id:");
		lbl2.setBounds(10, 39, 108, 20);
		lbl2.setVisible(false);
		lbl2.setFont(new Font("Tahoma", Font.PLAIN, 12));

		txtId = new JTextField();
		txtId.setBounds(150, 39, 150, 20);
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

		JLabel label = new JLabel("*Course Name:");
		label.setBounds(10, 70, 108, 20);
		label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().add(label);

		txtCourseName = new JTextField();
		txtCourseName.setBounds(150, 70, 150, 20);
		txtCourseName.setColumns(10);
		getContentPane().add(txtCourseName);

		JLabel label_1 = new JLabel("Instructor:");
		label_1.setBounds(10, 100, 108, 20);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().add(label_1);

		txtInstructor = new JTextField();
		txtInstructor.setBounds(150, 100, 150, 20);
		txtInstructor.setColumns(10);
		getContentPane().add(txtInstructor);
		// getContentPane().add(label_2);

		JLabel lblStartDatemmddyyyy = new JLabel("Start Date (MM/dd/yyyy)");
		lblStartDatemmddyyyy.setBounds(10, 130, 140, 20);
		lblStartDatemmddyyyy.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().add(lblStartDatemmddyyyy);

		txtSdate = new JFormattedTextField(ScreenUtil.createFormatter("##/##/####"));
		txtSdate.setBounds(150, 130, 150, 20);
		txtSdate.setColumns(10);
		getContentPane().add(txtSdate);

		JLabel lblEndDatemmddyyyy = new JLabel("End Date  (MM/dd/yyyy)");
		lblEndDatemmddyyyy.setBounds(10, 160, 140, 20);
		lblEndDatemmddyyyy.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().add(lblEndDatemmddyyyy);

		txtEdate = new JFormattedTextField(ScreenUtil.createFormatter("##/##/####"));
		txtEdate.setBounds(150, 160, 150, 20);
		txtEdate.setColumns(10);
		getContentPane().add(txtEdate);

		JLabel label_4 = new JLabel("* Required fields");
		label_4.setBounds(10, 191, 108, 20);
		label_4.setFont(new Font("Tahoma", Font.BOLD, 10));
		getContentPane().add(label_4);

		JLabel label_8 = new JLabel("");
		label_8.setBounds(0, 354, 150, 35);
		getContentPane().add(label_8);
		getContentPane().add(btnSave);

		btnGoBack = new JButton("Go Back to List");

		btnGoBack.setBackground(SystemColor.info);
		btnGoBack.setBounds(10, 261, 150, 23);
		getContentPane().add(btnGoBack);

		separator = new JSeparator();
		separator.setBounds(10, 253, 281, 9);
		getContentPane().add(separator);
	}
}
