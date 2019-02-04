package com.csis.reminder.view;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.csis.reminder.util.ScreenUtil;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 6433406218504113020L;
	private JPanel contentPane;


	/**
	 * Create the frame.
	 */
	public MainWindow() {
		init();
		setTitle("Reminder");
		setBounds(ScreenUtil.resizeScreen(0.7));
	}


	private void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menu = new JMenuBar();
		setJMenuBar(menu);
		
		JMenu mnFile = new JMenu("Users");
		menu.add(mnFile);
		
		JMenuItem mntmListUsers = new JMenuItem("List Users");
		mnFile.add(mntmListUsers);
		
		JMenuItem mntmNewUser = new JMenuItem("New User");
		mnFile.add(mntmNewUser);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 424, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGap(0, 231, Short.MAX_VALUE)
		);
		contentPane.setLayout(gl_contentPane);
	}
}
