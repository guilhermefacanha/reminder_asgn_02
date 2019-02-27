package com.csis.reminder.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
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

public class CourseFormView extends JInternalFrame {
	private static final long serialVersionUID = -7030310764143744275L;

	private CourseDAO coursedao = new CourseDAO();

	private User user;
	private Course course;
	private Long courseid;
	private JTextField txtCourseName;
	private JTextField txtInstructor;
	private JTextField txtSdate;
	private JTextField txtEdate;
	private JTextField txtId;
	private JLabel lbl2;
	// private JComboBox<UserType> userTypeCmb;
	// private JCheckBox chkEnable;
	private JLabel lblFormTitle;
	private JButton btnSave;
	// private JLabel lblPass;
	// private JLabel lblPassConf;
	
	public CourseFormView() {
	}

	/**
	 * Create the frame.
	 */
	public CourseFormView(User user) {
		this.courseid = null;
		this.user = user;

		init();
		config();
		createActions();
	}

	/**
	 * @param courseId,
	 *            user - courseID and User to be loaded and start the edit form
	 */
	public CourseFormView(long courseId, User user) {
		this(user);
		this.courseid = courseId;
		loadCourse(courseId);
	}

	private void loadCourse(long courseid) {
		course = coursedao.getCourse(courseid);
		if (course != null) {
			System.out.println(" entrei loadcourse");
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
		System.out.println(" entrei config");

	}

	private void createActions() {

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

					Course course = new Course();
					DateFormat formatter;
					formatter = new SimpleDateFormat("dd-MMM-yy");

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

	}

	private void init() {

		System.out.println(" entrei init");
		setBounds(100, 100, 317, 423);

		btnSave = new JButton("Save");
		btnSave.setBounds(118, 311, 150, 20);
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSave.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));

		lblFormTitle = new JLabel("New Course");
		lblFormTitle.setBounds(0, 4, 119, 27);
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
		txtCourseName.setBounds(118, 70, 150, 20);
		txtCourseName.setColumns(10);
		getContentPane().add(txtCourseName);

		JLabel label_1 = new JLabel("Instructor:");
		label_1.setBounds(10, 100, 108, 20);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().add(label_1);

		txtInstructor = new JTextField();
		txtInstructor.setBounds(118, 100, 150, 20);
		txtInstructor.setColumns(10);
		getContentPane().add(txtInstructor);
		// getContentPane().add(label_2);

		JLabel label_2 = new JLabel("Start Date");
		label_2.setBounds(10, 130, 108, 20);
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().add(label_2);

		txtSdate = new JTextField();
		txtSdate.setBounds(118, 130, 150, 20);
		txtSdate.setColumns(10);
		getContentPane().add(txtSdate);

		JLabel label_3 = new JLabel("End Date");
		label_3.setBounds(10, 160, 108, 20);
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().add(label_3);

		txtEdate = new JTextField();
		txtEdate.setBounds(118, 160, 150, 20);
		txtEdate.setColumns(10);
		getContentPane().add(txtEdate);
		

		JLabel label_4 = new JLabel("* Required fields");
		label_4.setBounds(10, 287, 108, 20);
		label_4.setFont(new Font("Tahoma", Font.BOLD, 10));
		getContentPane().add(label_4);

		JLabel label_8 = new JLabel("");
		label_8.setBounds(0, 354, 150, 35);
		getContentPane().add(label_8);
		getContentPane().add(btnSave);
	}

}
