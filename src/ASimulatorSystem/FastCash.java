package ASimulatorSystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.Date;

public class FastCash extends JFrame implements ActionListener {

	JLabel l1, l2;
	JButton b1, b2, b3, b4, b5, b6, b7, b8;
	JTextField t1;
	String pin;

	FastCash(String LoginName) {
		this.pin = pin;
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("ASimulatorSystem/icons/atm.jpg"));
		Image i2 = i1.getImage().getScaledInstance(960, 900, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		JLabel l3 = new JLabel(i3);
		l3.setBounds(0, 0, 960, 900);
		add(l3);

		l1 = new JLabel("SELECT WITHDRAWL AMOUNT");
		l1.setForeground(Color.WHITE);
		l1.setFont(new Font("System", Font.BOLD, 16));

		b1 = new JButton("RON 100");
		b2 = new JButton("RON 500");
		b3 = new JButton("RON 1000");
		b4 = new JButton("RON 2000");
		b5 = new JButton("RON 5000");
		b6 = new JButton("RON 10000");
		b7 = new JButton("BACK");

		setLayout(null);

		l1.setBounds(235, 290, 700, 35);
		l3.add(l1);

		b1.setBounds(170, 389, 150, 35);
		l3.add(b1);

		b2.setBounds(390, 389, 150, 35);
		l3.add(b2);

		b3.setBounds(170, 433, 150, 35);
		l3.add(b3);

		b4.setBounds(390, 433, 150, 35);
		l3.add(b4);

		b5.setBounds(170, 478, 150, 35);
		l3.add(b5);

		b6.setBounds(390, 478, 150, 35);
		l3.add(b6);

		b7.setBounds(390, 523, 150, 35);
		l3.add(b7);

		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		b6.addActionListener(this);
		b7.addActionListener(this);

		setSize(960, 900);
		setLocation(500, 0);
		setUndecorated(true);
		setVisible(true);

	}

	public void actionPerformed(ActionEvent ae) {
		try {
			String amount = ((JButton) ae.getSource()).getText().substring(4); 
			Conn c = new Conn();
			ResultSet rs = c.s.executeQuery("select * from bank where pin = '" + pin + "'");
			int balance = 0;
			while (rs.next()) {
				if (rs.getString("mode").equals("Deposit")) {
					balance += Integer.parseInt(rs.getString("amount").replaceAll("\\s+",""));
				} else {
					balance -= Integer.parseInt(rs.getString("amount").replaceAll("\\s+",""));
				}
			}
			String num = "17";
			
			if (ae.getSource() != b7 && balance < Integer.parseInt(amount.replaceAll("\\s+",""))) {
				JOptionPane.showMessageDialog(null, "Insuffient Balance");
				return;
			}

			if (ae.getSource() == b7) {
				this.setVisible(false);
				new Transactions(pin).setVisible(true);
			} else {
				Date date = new Date();
				c.s.executeUpdate(
						"insert into bank values('" + pin + "', '" + date + "', 'Withdrawl', '" + amount + "')");
				JOptionPane.showMessageDialog(null, "RON. " + amount + " Debited Successfully");

				setVisible(false);
				new Transactions(pin).setVisible(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new FastCash("").setVisible(true);
	}
}
