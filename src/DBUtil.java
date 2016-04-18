import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;


public class DBUtil {
	
	static Connection conn;
	static String driver="com.mysql.jdbc.Driver";
	static String url="jdbc:mysql://localhost:3306/lucrarelicenta";
	static String user="gt";
	static String password="parola";

	public static Connection getConnection() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			conn=DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(conn!=null)
		{
			System.out.println("Conectare Reusita");
		}
		else
			System.out.println("Conectare Esuata");
		return conn;
	}
}