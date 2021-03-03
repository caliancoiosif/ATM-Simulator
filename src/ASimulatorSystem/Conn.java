package ASimulatorSystem;

import java.sql.*;

public class Conn {
	Connection c;
	Statement s;

	public Conn() {
		try {
			c = DriverManager.getConnection("jdbc:mysql://localhost/bms?" + "user=root&password=");
			s = c.createStatement();

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}