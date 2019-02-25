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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.csis.reminder.dao.UserDAO;
import com.csis.reminder.entity.User;
import javax.swing.ListSelectionModel;

@SuppressWarnings("rawtypes")
public class UserListView extends JInternalFrame {
	private static final long serialVersionUID = -5005775842044227857L;

	private UserDAO userdao = new UserDAO();

	private JDesktopPane desktop;
	private DefaultTableModel dtm;
	private JTable tbUsers;
	private JScrollPane scrollPane;
	private JButton btnNewUser;
	private JButton btnEditUser;
	private JButton btnDeleteUser;

	/**
	 * Create the frame.
	 */
	public UserListView(JDesktopPane desktop) {
		this.desktop = desktop;

		setIconifiable(true);
		init();
		config();
		loadUsers();
		createActions();
	}

	private void config() {
		setTitle("Users List");
		setClosable(true);
		setMaximizable(true);
		setResizable(true);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		tbUsers.setDefaultRenderer(String.class, centerRenderer);
		tbUsers.setDefaultRenderer(Long.class, centerRenderer);
	}

	private void loadUsers() {
		dtm = (DefaultTableModel) tbUsers.getModel();
		List<User> users = userdao.getAllUsers();
		for (User u : users) {
			dtm.addRow(u.getData());
		}
	}

	private void createActions() {
		// button add user action
		btnNewUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// create a new user form window to show
				UserFormView form = new UserFormView();
				desktop.add(form);
				form.show();

				// close current window
				dispose();
			}
		});

		// button edit user action
		btnEditUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tbUsers.getSelectedRow();
				if (selectedRow > -1) {
					Long id = (Long) tbUsers.getValueAt(selectedRow, 0);
					UserFormView form = new UserFormView(id);
					desktop.add(form);
					form.show();
					
					// close current window
					dispose();
				} else
					JOptionPane.showMessageDialog(getContentPane(), "Select a row to edit!", "Error",
							JOptionPane.ERROR_MESSAGE);
			}
		});

		// button delete user action
		btnDeleteUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tbUsers.getSelectedRow();
				if (selectedRow > -1) {
					Long id = (Long) tbUsers.getValueAt(selectedRow, 0);
					int confirm = JOptionPane.showConfirmDialog(getContentPane(),
							"Confirm delete user with id " + id + " ?");
					if (confirm == 0) {
						userdao.deleteUser(id);
						dtm.removeRow(selectedRow);
						JOptionPane.showMessageDialog(getContentPane(), "User deleted", "Info",
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

		btnNewUser = new JButton("New");

		btnEditUser = new JButton("Edit");

		btnDeleteUser = new JButton("Delete");

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup().addComponent(btnNewUser)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnEditUser, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnDeleteUser)))
				.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnNewUser)
								.addComponent(btnEditUser).addComponent(btnDeleteUser))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE).addGap(9)));

		tbUsers = new JTable();
		tbUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(tbUsers);
		tbUsers.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Id", "Email", "First Name", "Last Name", "Type", "Enabled" }) {
			Class[] columnTypes = new Class[] { Long.class, String.class, String.class, String.class, String.class,
					Boolean.class };

			@SuppressWarnings("unchecked")
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tbUsers.getColumnModel().getColumn(0).setResizable(true);
		getContentPane().setLayout(groupLayout);
	}
}
