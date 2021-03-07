package ASimulatorSystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class BankUsers extends JFrame implements ActionListener {

	JButton b1, b2;
	JTextArea l1, l3;
	JLabel l2;

	BankUsers(String user) {
		super("Bank Users");
		getContentPane().setBackground(Color.WHITE);
		setSize(500, 850);
		setLocation(20, 20);

		l1 = new JTextArea();
		l1.setBounds(10, 50, 300, 300);
		add(l1);

		JLabel l2 = new JLabel("Bank Users");
		l2.setBounds(150, 20, 100, 20);
		add(l2);

		l3 = new JTextArea();
		l3.setBounds(10, 360, 300, 300);
		add(l3);

		try {
			Conn c = new Conn();
			ResultSet rs = c.s.executeQuery("SELECT * FROM login where pin ");
			while (rs.next()) {
				l3.setText(l3.getText() + "Password key.: " + rs.getString("pin") + "\n");
			}
		} catch (Exception e) {
		}

		try {

			Conn c1 = new Conn();
			ResultSet rs = c1.s.executeQuery("SELECT * FROM login where cardno");
			while (rs.next()) {
				l1.setText(l1.getText() + "Login Key.: " + rs.getString("cardno") + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		setLayout(null);
		b1 = new JButton("Exit");
		add(b1);

		b1.addActionListener(this);
		b1.setBounds(20, 700, 100, 25);
	}

	public void actionPerformed(ActionEvent ae) {
		this.setVisible(false);
	}

	public static void main(String[] args) {
		new BankUsers("").setVisible(true);
	}

}
