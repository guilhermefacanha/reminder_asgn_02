package com.csis.hello;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Hello World Application
 * Reminder Group
 * - Guilherme Facanha #300294067
 * - João Vitor Wilke Silva #300278748
 */
public class HelloWorld extends JFrame {

	private static final long serialVersionUID = 3751257714239101844L;
	private JPanel contentPane;
	private JButton btnHello;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HelloWorld frame = new HelloWorld();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public HelloWorld() {
		setupComponents();
		setupAction();
	}

	private void setupComponents() {
		setTitle("Hello World Application");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnHello = new JButton("Hello");
		btnHello.setBounds(10, 11, 89, 23);
		contentPane.add(btnHello);
	}

	private void setupAction() {
		btnHello.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(getContentPane(), "Hello World Application");
			}
		});
	}
}
