package com.csis.reminder.view;


	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.util.*;
	

	import javax.swing.GroupLayout;
	import javax.swing.GroupLayout.Alignment;
	import javax.swing.JButton;
	import javax.swing.JDesktopPane;
	import javax.swing.JInternalFrame;
	import javax.swing.JLabel;
	import javax.swing.JOptionPane;
	import javax.swing.JScrollPane;
	import javax.swing.JTable;
	import javax.swing.LayoutStyle.ComponentPlacement;
	import javax.swing.table.DefaultTableCellRenderer;
	import javax.swing.table.DefaultTableModel;

	import com.csis.reminder.dao.CourseDAO;
	import com.csis.reminder.entity.Course;
	import javax.swing.ListSelectionModel;
	import com.csis.reminder.dao.UserDAO;
	import com.csis.reminder.entity.User;
	import com.csis.reminder.entity.enumeration.UserType;

	@SuppressWarnings("rawtypes")
	
	public class CourseListView extends JInternalFrame
	{
		private static final long serialVersionUID = -5005775842044227857L;

		private CourseDAO coursedao = new CourseDAO();

		private JDesktopPane desktop;
		private DefaultTableModel dtm;
		private JTable tbCourses;
		private JScrollPane scrollPane;
		private JButton btnNewCourse;
		private JButton btnEditCourse;
		private JButton btnDeleteCourse;
		private User user ;

		/**
		 * Create the frame.
		 */
		public CourseListView(JDesktopPane desktop, User user) {
			this.desktop = desktop;
			this.user = user ;

			setIconifiable(true);
			init();
			config();
			loadCourses();
			createActions();
		}

		private void config() {
			setTitle("Course List");
			setClosable(true);
			setMaximizable(true);
			setResizable(true);

			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(JLabel.CENTER);
			tbCourses.setDefaultRenderer(String.class, centerRenderer);
			tbCourses.setDefaultRenderer(Long.class, centerRenderer);
		}

		private void loadCourses() {
			dtm = (DefaultTableModel) tbCourses.getModel();
			List<Course> courses = coursedao.getAllCourses(user);
			int i=0 ;
		    for (Course C : courses) {
				   System.out.println("Element "+ courses.get(i).getId());
				   System.out.println("Element "+ courses.get(i).getCourseName());
				   System.out.println("Element "+ courses.get(i).getCourseInstructor());
				   System.out.println("Element "+ courses.get(i).getStartDate());
				   System.out.println("Element "+ courses.get(i).getEndDate());
				   i++;
			  dtm.addRow(C.getData());
			}
			
		//	for (int i=0; i<courses.size(); i++){
	
		}

		private void createActions() {
			// button add course action
			btnNewCourse.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					// create a new Course form window to show
					CourseFormView form = new CourseFormView(user);
					desktop.add(form);
					form.show();

					// close current window
					dispose();
				}
			});

			// button edit course action
			btnEditCourse.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int selectedRow = tbCourses.getSelectedRow();
					if (selectedRow > -1) {
						Long id = (Long) tbCourses.getValueAt(selectedRow, 0);
						CourseFormView form = new CourseFormView( id, user);
						desktop.add(form);
						form.show();
						
						// close current window
						dispose();
					} else
						JOptionPane.showMessageDialog(getContentPane(), "Select a row to edit!", "Error",
								JOptionPane.ERROR_MESSAGE);
				}
			});

			// button delete course action
			btnDeleteCourse.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int selectedRow = tbCourses.getSelectedRow();
					if (selectedRow > -1) {
						Long id = (Long) tbCourses.getValueAt(selectedRow, 0);
						int confirm = JOptionPane.showConfirmDialog(getContentPane(),
								"Confirm delete Course with id " + id + " ?");
						if (confirm == 0) {
							coursedao.deleteCourse(id);
							dtm.removeRow(selectedRow);
							JOptionPane.showMessageDialog(getContentPane(), "Course deleted", "Info",
									JOptionPane.INFORMATION_MESSAGE);
						}
					} else
						JOptionPane.showMessageDialog(getContentPane(), "Select a row to delete!", "Error",
								JOptionPane.ERROR_MESSAGE);
				}
			});
		}

		@SuppressWarnings("serial")
		private void init() {
			setBounds(100, 100, 708, 367);

			scrollPane = new JScrollPane();

			btnNewCourse = new JButton("New");

			btnEditCourse = new JButton("Edit");

			btnDeleteCourse = new JButton("Delete");

			GroupLayout groupLayout = new GroupLayout(getContentPane());
			groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
					.createSequentialGroup().addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE)
							.addGroup(groupLayout.createSequentialGroup().addComponent(btnNewCourse)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnEditCourse, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnDeleteCourse)))
					.addContainerGap()));
			groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
					.addGroup(groupLayout.createSequentialGroup().addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnNewCourse)
									.addComponent(btnEditCourse).addComponent(btnDeleteCourse))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE).addGap(9)));

			tbCourses = new JTable();
			tbCourses.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			scrollPane.setViewportView(tbCourses);
			tbCourses.setModel(new DefaultTableModel(new Object[][] {},
					new String[] { "Id", "Course Name", "Instructor", "Start Date", "End Date" }) {
				Class[] columnTypes = new Class[] {Long.class, String.class, String.class, String.class, String.class, String.class};

				@SuppressWarnings("unchecked")
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}

				boolean[] columnEditables = new boolean[] { false, false, false, false, false };

				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
			tbCourses.getColumnModel().getColumn(0).setResizable(true);
			getContentPane().setLayout(groupLayout);
		}
 }

	
