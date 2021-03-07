
package ASimulatorSystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Date;
import java.sql.*;

public class Withdrawl extends JFrame implements ActionListener {

	JTextField t1, t2;
	JButton b1, b2, b3;
	JLabel l1, l2, l3, l4;
	String pin;

	Withdrawl(String pin) {
		this.pin = pin;
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("ASimulatorSystem/icons/atm.jpg"));
		Image i2 = i1.getImage().getScaledInstance(960, 900, Image.SCALE_AREA_AVERAGING);
		ImageIcon i3 = new ImageIcon(i2);
		JLabel l3 = new JLabel(i3);
		l3.setBounds(0, 0, 960, 900);
		add(l3);

		l1 = new JLabel("MAXIMUM WITHDRAWAL IS RON.10,000");
		l1.setForeground(Color.WHITE);
		l1.setFont(new Font("System", Font.BOLD, 16));

		l2 = new JLabel("PLEASE ENTER YOUR AMOUNT");
		l2.setForeground(Color.WHITE);
		l2.setFont(new Font("System", Font.BOLD, 16));

		t1 = new JTextField();
		t1.setFont(new Font("Raleway", Font.BOLD, 25));

		b1 = new JButton("WITHDRAW");
		b2 = new JButton("BACK");

		setLayout(null);

		l1.setBounds(190, 240, 400, 20);
		l3.add(l1);

		l2.setBounds(190, 290, 400, 20);
		l3.add(l2);

		t1.setBounds(190, 340, 330, 30);
		l3.add(t1);

		b1.setBounds(390, 478, 150, 35);
		l3.add(b1);

		b2.setBounds(390, 523, 150, 35);
		l3.add(b2);

		b1.addActionListener(this);
		b2.addActionListener(this);

		setSize(960, 900);
		setLocation(500, 0);
		setUndecorated(true);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {
		try {
			String amount = t1.getText();
			Date date = new Date();
			if (ae.getSource() == b1) {
				if (t1.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter the Amount to you want to Withdraw");
				} else {
					Conn c1 = new Conn();

					ResultSet rs = c1.s.executeQuery("select * from bank where pin = '" + pin + "'");
					int balance = 0;
					while (rs.next()) {
						if (rs.getString("mode").equals("Deposit")) {
							balance += Integer.parseInt(rs.getString("amount"));
						} else {
							balance -= Integer.parseInt(rs.getString("amount"));
						}
					}
					if (balance < Integer.parseInt(amount)) {
						JOptionPane.showMessageDialog(null, "Insuffient Balance");
						return;
					}

					c1.s.executeUpdate(
							"insert into bank values('" + pin + "', '" + date + "', 'Withdrawl', '" + amount + "')");
					JOptionPane.showMessageDialog(null, "RON. " + amount + " Debited Successfully");

					setVisible(false);
					new Transactions(pin).setVisible(true);
				}
			} else if (ae.getSource() == b2) {
				setVisible(false);
				new Transactions(pin).setVisible(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error: " + e);
		}

	}

	public static void main(String[] args) {
		new Withdrawl("").setVisible(true);
	}
}
