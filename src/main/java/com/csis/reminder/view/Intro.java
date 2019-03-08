package com.csis.reminder.view;

import java.awt.Font;
import java.awt.SystemColor;
import java.io.IOException;
import java.nio.file.Paths;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import com.csis.reminder.dao.UserDAO;
import com.csis.reminder.util.ScreenUtil;

/**
 * @author Reminder Group
 * Class responsible for the splash screen for the system
 */
public class Intro extends JFrame {

	private static final long serialVersionUID = -1430262372419051006L;
	private JPanel panel;

	final String homeDirectory = System.getProperty("user.dir");
	final String imgDirectory = Paths.get(homeDirectory, "img").toString();
	final String img = imgDirectory + "/" + "loader.gif";
	private JLabel lblImage;
	private JLabel lblNewLabel_1;
	
	/**
	 * Launch the application.
	 * main method is used to be the entry point of the System
	 */
	public static void main(String[] args) {

		try {
			//create and start the frame
			Intro frame = new Intro();
			frame.setVisible(true);
			frame.start();
			
			
			//load hibernate context
			try {
				UserDAO userDao = new UserDAO();
				userDao.initSelec();
								
				Login login = new Login();
				if (!login.isVisible()) {
					login.setVisible(true);
				}
			} catch (Exception ex) {
			}
			
			frame.setVisible(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Create the Intro Frame
	 */
	public Intro() {

		init();
		ScreenUtil.centerWindow(this);
		lblImage.setVisible(true);
	}
	
	
	/**
	 * Method to start the render of image
	 */
	private void start() {
        new ImageWorker().execute();
    }

	/**
	 * Method that stores all GUI automatic generated code
	 */
	private void init() {
		setBounds(100, 100, 450, 300);
		setResizable(false);
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		getContentPane().setBackground(SystemColor.inactiveCaptionBorder);
		setBounds(10, 10, 450, 300);

		panel = new JPanel();
		panel.setBackground(SystemColor.textInactiveText);

		JLabel lblNewLabel = new JLabel("Reminder");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 35));

		JLabel lblV = new JLabel(" v1.1");
		lblV.setHorizontalAlignment(SwingConstants.CENTER);
		lblV.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 16));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(254, Short.MAX_VALUE)
					.addComponent(lblV, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
					.addGap(148))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(77)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblV)
					.addPreferredGap(ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE))
		);
		
				lblImage = new JLabel(new ImageIcon(img));
				lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblNewLabel_1 = new JLabel("Connecting to database...");
		lblNewLabel_1.setForeground(SystemColor.info);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblImage, GroupLayout.PREFERRED_SIZE, 430, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap(394, Short.MAX_VALUE)
					.addComponent(lblNewLabel_1)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(28)
					.addComponent(lblImage, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(lblNewLabel_1)
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		getContentPane().setLayout(groupLayout);
	}
	
	/**
	 * Auxiliary class to render the image before the hibernate load process
	 *
	 */
	class ImageWorker extends SwingWorker<Icon, Void> {

        private ImageIcon icon;

        @Override
        protected Icon doInBackground() throws IOException {
            icon = new ImageIcon(img);
            return icon;
        }

        @Override
        protected void done() {
            lblImage.setIcon(icon);
            Intro.this.pack();
        }
    }
}
