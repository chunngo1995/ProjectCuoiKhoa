package pk;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {
	/**
	 *
	 * @author chunk
	 * 
	 */
	public static Connection getConnection() {
		final String url="jdbc:mysql://localhost:3306/project";
		final String user ="root";
		final String password="chunngo1";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	public static void main(String args[]) {
		Connection con=getConnection();
		if (con!=null) {
			System.out.println("thanh cong");
		}
		else
			System.out.println("that bai");

	}
}
