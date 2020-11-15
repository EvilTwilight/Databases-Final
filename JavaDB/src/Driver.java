import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) throws Exception {
		
		createMoviesTable();
		createCastTable();
		createActsInTable();
		//firstGet(scan);
		//dropTable();
		
		
		//Douglas
		swingContainer swingContainer = new swingContainer();  
		swingContainer.showJFrameDemo();
		
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
			//"jdbc:mysql://localhost:3306/test", "root", "1234"
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "fattycat123"); //replace db name, username, and password accordingly
			return conn;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//Query one, number 5 in proposal
	public static void firstGet(String userInput) throws Exception { // (5) query to find movie from an actor that has the higher score
		try {
			//Scanner scan
			Connection con = getConnection();
			//scan.nextLine();
			String input = userInput;
			PreparedStatement statement = con.prepareStatement("SELECT M.title FROM movies M, cast C, acts_in A WHERE C.name = \"" + input + "\" AND C.aid = A.aid AND M.mid = A.mid GROUP BY M.title ORDER BY M.score DESC LIMIT 1");
			
			ResultSet result = statement.executeQuery();
			
			while(result.next()) {
				System.out.println(result.getString("M.title"));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			System.out.println("Query 1 complete.");
		}
	}
}
