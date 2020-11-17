package databasesProject;

import java.sql.*;
import java.io.FileReader;
import java.io.BufferedReader;

public class MovieDatabaseMain {
	static String movie_name_score_path = "movie-name-score.txt";
	static String movie_cast_path = "movie-cast.txt";
	
	public static void main(String[] args) throws Exception {
		createMoviesTable();
		createCastTable();
		createActsInTable();
		//dropTable();
		import_movie_table();
		import_cast_table();
		import_actsin_table();
	}
	
	public static String clean_middle_commas(String input_string) {
		String reg_exp_pattern = "(?<=\")([^\"]+?),([^\"]+?)(?=\")";
		String original_string = input_string;
		String output_string = input_string.replaceAll(reg_exp_pattern, "$1|$2");
		while (!output_string.equalsIgnoreCase(original_string)) {
			original_string = output_string;
			output_string = output_string.replaceAll(reg_exp_pattern, "$1|$2");
		}
		return output_string;
	}
	
	public static void import_movie_table() throws Exception{
		String sql_movies_insert = "INSERT INTO movies (movie_id, title, score) VALUES (?, ?, ?)";
		String raw_lines = null;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(movie_name_score_path));
			Connection con = getConnection();
			con.setAutoCommit(false);
			PreparedStatement sql_query = con.prepareStatement(sql_movies_insert);
			
			while((raw_lines = reader.readLine()) != null) {
				String next_raw_line = clean_middle_commas(raw_lines);
				// System.out.println("The next cleaned line is: " + next_raw_line);
				String[] raw_csv_data = next_raw_line.split(",");
				
				int movie_id = Integer.parseInt(raw_csv_data[0]);
				String movie_title = raw_csv_data[1].replace("\"", "");
				int movie_score = Integer.parseInt(raw_csv_data[2]);
			
				sql_query.setInt(1, movie_id);
				sql_query.setString(2, movie_title);
				sql_query.setInt(3, movie_score);
				sql_query.addBatch();
				sql_query.executeBatch();
				
			}
			reader.close();
			sql_query.executeBatch();
			con.commit();
			con.close();
			System.out.println("Function complete.");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void import_cast_table() throws Exception{
		String sql_cast_insert = "INSERT INTO cast (movie_id, actor_id, name) VALUES (?, ?, ?)";
		String raw_lines = null;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(movie_cast_path));
			Connection con = getConnection();
			con.setAutoCommit(false);
			PreparedStatement sql_query = con.prepareStatement(sql_cast_insert);
			
			while((raw_lines = reader.readLine()) != null) {
				String next_raw_line = clean_middle_commas(raw_lines);
				String[] raw_csv_data = next_raw_line.split(",");
				/*
				for (int i = 0; i < raw_csv_data.length; i++) {
					System.out.println("TEST: " + "\"" + i + ":\"" + raw_csv_data[i]);
				}
				*/
				int movie_id = Integer.parseInt(raw_csv_data[0]);
				int cast_id = Integer.parseInt(raw_csv_data[1]);
				String cast_fullname = raw_csv_data[2].replace("\"", "");
				// System.out.println("cast_fullname after replace: " + cast_fullname);
				
				sql_query.setInt(1, movie_id);
				sql_query.setInt(2, cast_id);
				sql_query.setString(3, cast_fullname);
				
				sql_query.addBatch();
				sql_query.executeBatch();
			}
			reader.close();
			sql_query.executeBatch();
			con.commit();
			con.close();
			System.out.println("Function complete.");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	public static void import_actsin_table() throws Exception{
		String sql_cast_insert = "INSERT INTO acts_in (actor_id, movie_id) VALUES (?, ?);";
		try {
			BufferedReader reader_castnames = new BufferedReader(new FileReader(movie_cast_path));
			Connection con = getConnection();
			//con.setAutoCommit(false);
			PreparedStatement sql_query = con.prepareStatement(sql_cast_insert);
			
			String casts_lines;
			while ((casts_lines = reader_castnames.readLine()) != null) {
				String cleaned_castnames_lines = clean_middle_commas(casts_lines);
				String[] input_array_castnames = cleaned_castnames_lines.split(",");
				int movie_id = Integer.parseInt(input_array_castnames[0]);				
				int actor_id = Integer.parseInt(input_array_castnames[1].replace("\"", ""));

				
				System.out.println("Attempting to insert actor_id: " + actor_id + " movie_id: " + movie_id);
			
				sql_query.setInt(1, movie_id);
				sql_query.setInt(2, actor_id);
				sql_query.addBatch();
			}
			sql_query.addBatch();
			sql_query.executeBatch();
			reader_castnames.close();
			con.commit();
			System.out.println("Function complete.");
			con.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	public static void createMoviesTable() throws Exception{
		try {
			Connection con = getConnection();
			PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS movies (movie_id int, title varchar(255), score int, PRIMARY KEY (movie_id))");
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
			PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS cast (movie_id int, actor_id int, name varchar(255), PRIMARY KEY (movie_id, actor_id))");
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
			PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS acts_in (actor_id int, movie_id int, PRIMARY KEY (actor_id, movie_id), FOREIGN KEY(movie_id, actor_id) REFERENCES cast (movie_id, actor_id), FOREIGN KEY(movie_id) REFERENCES movies(movie_id))");
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
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/movies", "newuser", "1234"); //replace db name, username, and password accordingly
			return conn;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}