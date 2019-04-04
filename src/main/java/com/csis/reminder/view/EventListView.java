package com.csis.reminder.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.csis.reminder.dao.CourseDAO;
import com.csis.reminder.dao.EventDAO;
import com.csis.reminder.entity.Event;
import com.csis.reminder.entity.User;

@SuppressWarnings("rawtypes")

public class EventListView extends JInternalFrame {
	private static final long serialVersionUID = -5005775842044227857L;

	private EventDAO eventDAO = new EventDAO();

	private JDesktopPane desktop;
	private DefaultTableModel dtm;
	private JTable tbEvents;
	private JScrollPane scrollPane;
	private JButton btnNewCourse;
	private JButton btnEditCourse;
	private JButton btnDeleteCourse;
	private User user;

	/**
	 * Create the frame.
	 */
	public EventListView(JDesktopPane desktop, User user) {
		this.desktop = desktop;
		this.user = user;

		setIconifiable(true);
		init();
		config();
		loadCourses();
		createActions();
	}

	private void config() {
		setTitle("Event List");
		setClosable(true);
		setMaximizable(true);
		setResizable(true);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		tbEvents.setDefaultRenderer(String.class, centerRenderer);
		tbEvents.setDefaultRenderer(Long.class, centerRenderer);
	}

	private void loadCourses() {
		dtm = (DefaultTableModel) tbEvents.getModel();
		List<Event> events = eventDAO.getAllEvents(user);
		for (Event event : events) {
			dtm.addRow(event.getData());
		}

	}

	private void createActions() {
		// button add course action
		btnNewCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// checks if the user has any courses registered
				if(new CourseDAO().hasUserCourse(user)) {
					// create a new Course form window to show
					EventFormView form = new EventFormView(user,desktop);
					desktop.add(form);
					form.show();	
					// close current window
					dispose();
				}
				else	{
					JOptionPane.showMessageDialog(null, "You must have at least one Course in order to create an Event.");
				}				
			}
		});

		// button edit course action
		btnEditCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tbEvents.getSelectedRow();
				if (selectedRow > -1) {
					Long id = (Long) tbEvents.getValueAt(selectedRow, 0);
					EventFormView form = new EventFormView(id, user,desktop);
					desktop.add(form);
					form.show();

					// close current window
					dispose();
				} else
					JOptionPane.showMessageDialog(getContentPane(), "Select a row to edit!", "Error",
							JOptionPane.ERROR_MESSAGE);
			}
		});

		// button delete event action
		btnDeleteCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tbEvents.getSelectedRow();
				if (selectedRow > -1) {
					Long id = (Long) tbEvents.getValueAt(selectedRow, 0);
					int confirm = JOptionPane.showConfirmDialog(getContentPane(),
							"Confirm delete event with id " + id + " ?");
					if (confirm == 0) {
						try {
							eventDAO.deleteEvent(id);
							dtm.removeRow(selectedRow);
							JOptionPane.showMessageDialog(getContentPane(), "Event deleted", "Info",
									JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(getContentPane(), e2.getMessage(), "Error",
									JOptionPane.ERROR_MESSAGE);
						}
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

		tbEvents = new JTable();
		tbEvents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(tbEvents);
		tbEvents.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Id", "Course Name", "Event Type", "Date", "Event Name", "Description" }) {
			Class[] columnTypes = new Class[] { Long.class, String.class, String.class, String.class, String.class,
					String.class };

			@SuppressWarnings("unchecked")
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tbEvents.getColumnModel().getColumn(0).setResizable(true);
		getContentPane().setLayout(groupLayout);
	}
}
