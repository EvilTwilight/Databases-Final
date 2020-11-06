import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Driver {

	public static void main(String[] args) throws Exception {
		createMoviesTable();
		createCastTable();
		createActsInTable();
		//dropTable();
	}
	
	public static void createMoviesTable() throws Exception{
		try {
			Connection con = getConnection();
			PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS movies (mid int, title varchar(255), score int, PRIMARY KEY (mid))");
			create.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			System.out.println("Function complete.");
		}
	}
	
	public static void createCastTable() throws Exception{
		try {
			Connection con = getConnection();
			PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS cast (aid int, name varchar(255), PRIMARY KEY (aid))");
			create.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			System.out.println("Function complete.");
		}
	}
	
	public static void createActsInTable() throws Exception{
		try {
			Connection con = getConnection();
			PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS acts_in (aid int, mid int, PRIMARY KEY (aid,mid), FOREIGN KEY(aid) REFERENCES cast (aid), FOREIGN KEY(mid) REFERENCES movies(mid))");
			create.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			System.out.println("Function complete.");
		}
	}
	
	public static void dropTable() throws Exception{
		try {
			Connection con = getConnection();
			Statement delete = con.createStatement();
			delete.execute("drop table x"); // replace x with table name
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			System.out.println("Function complete.");
		}
	}
	
	public static Connection getConnection() throws Exception{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234"); //replace db name, username, and password accordingly
			return conn;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
