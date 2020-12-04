import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Driver {
	static String movie_name_score_path = "movie-name-score.txt";
	static String movie_cast_path = "movie-cast.txt";
        
        
        static swingContainer swingContainer;
	public static void main(String[] args) throws Exception {
		
        try{     
            createMoviesTable();
            createCastTable();
            createActsInTable();

            import_movie_table();
            import_cast_table();
            import_actsin_table();
		}catch(Exception e){
            System.out.println(e);
        }
		//Douglas
		swingContainer = new swingContainer();  
		
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
		String sql_movies_insert = "INSERT IGNORE INTO movies (mid, title, score) VALUES (?, ?, ?)";
        
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
		String sql_cast_insert = "INSERT IGNORE INTO cast (aid, name) VALUES (?, ?)";
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
				
				int cast_id = Integer.parseInt(raw_csv_data[1]);
				String cast_fullname = raw_csv_data[2].replace("\"", "");
				// System.out.println("cast_fullname after replace: " + cast_fullname);
				
				sql_query.setInt(1, cast_id);
				sql_query.setString(2, cast_fullname);
				
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
		String sql_cast_insert = "INSERT IGNORE INTO acts_in (aid, mid) VALUES (?, ?);";
		try {
			BufferedReader reader_castnames = new BufferedReader(new FileReader(movie_cast_path));
			Connection con = getConnection();
			con.setAutoCommit(false);
			PreparedStatement sql_query = con.prepareStatement(sql_cast_insert);
			
			String casts_lines = null;
			while ((casts_lines = reader_castnames.readLine()) != null) {
				String cleaned_castnames_lines = clean_middle_commas(casts_lines);
				String[] input_array_castnames = cleaned_castnames_lines.split(",");
				int movie_id = Integer.parseInt(input_array_castnames[0]);				
				int actor_id = Integer.parseInt(input_array_castnames[1].replace("\"", ""));

				
				// System.out.println("Attempting to insert actor_id: " + actor_id + " movie_id: " + movie_id);
			
				sql_query.setInt(1, actor_id);
				sql_query.setInt(2, movie_id);
				sql_query.addBatch();
				sql_query.executeBatch();
			}
			sql_query.addBatch();
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
	
	//Query one, number 5 in proposal
	public static void firstGet(String userInput) throws Exception { // (5) query to find movie from an actor that has the highet score
		try {
			//Scanner scan
			Connection con = getConnection();
			//scan.nextLine();
			String input = userInput;
            
            PreparedStatement statement = con.prepareStatement("SELECT M.title FROM movies M, cast C, acts_in A WHERE C.name = \"" + input + "\" AND C.aid = A.aid AND M.mid = A.mid GROUP BY M.mid ORDER BY M.score DESC LIMIT 1");
            
			ResultSet result = statement.executeQuery();
			
            swingContainer.createOutputFrame();
			while(result.next()) {
                            String output = input + "'s best movie is " + result.getString("M.title");
                            swingContainer.addOutputToGui(output);
                            System.out.println(output);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			System.out.println("Query 1 complete.");
		}
	}
	
	//Query two, number 6 in proposal
	public static void secondGet(String userInput) throws Exception { // (6) query to find movies using a user inputted keyword
		try {
			Connection con = getConnection();
			PreparedStatement statement = con.prepareStatement("SELECT M.title FROM movies M WHERE title LIKE \"%" + userInput +"%\"");
			ResultSet result = statement.executeQuery();
				
                        swingContainer.createOutputFrame();
			while(result.next()) {
                            String output = result.getString("M.title");
                            swingContainer.addOutputToGui(output);
                            System.out.println(output);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			System.out.println("Query 2 complete.");
		}
	}
	
	//Query three, number 7 in proposal
	public static void thirdGet() throws Exception { // (7) query to find the top 10 most popular actors based on their average movie scores.
		try {
			Connection con = getConnection();
			PreparedStatement statement = con.prepareStatement("SELECT C.name, AVG(M.score) as avg_score FROM movies M, cast C, acts_in A WHERE C.aid = A.aid AND A.mid = M.mid GROUP BY C.name ORDER BY avg_score DESC LIMIT 10");
			ResultSet result = statement.executeQuery();
				
                        
                        swingContainer.createOutputFrame();
			while(result.next()) {
                            String output = result.getString("C.name") + " has an average movie score of " + result.getString("avg_score");
                            swingContainer.addOutputToGui(output);
                            System.out.println(output);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			System.out.println("Query 3 complete.");
		}
	}
	
	//Query four, number 8 in proposal
	public static void fourthGet() throws Exception { // (8) query to find a pair of actors/actresses that have starred in the most amount of movies together
		try {
			Connection con = getConnection();
			PreparedStatement statement = con.prepareStatement("SELECT C.name, D.name, COUNT(M.mid) as count FROM cast C, cast D, acts_in A, acts_in B, movies M WHERE C.aid!=D.aid AND C.aid=A.aid AND D.aid=B.aid AND A.mid=B.mid AND A.mid=M.mid GROUP BY C.name, D.name ORDER BY count DESC LIMIT 1");
			ResultSet result = statement.executeQuery();
				
                        swingContainer.createOutputFrame();
			while(result.next()) {
                            String output = result.getString("C.name") + " and " + result.getString("D.name") + " have acted in " + result.getString("count") + " movies together.";
                            swingContainer.addOutputToGui(output);
                            System.out.println(output);
			}
                        
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			System.out.println("Query 4 complete.");
		}
	}
	
	//Query five, number 9 in proposal (finds the movie with the largest cast size)
	public static void fifthGet() throws Exception { // (9) query to find the movie with the largest cast size
		try {
			Connection con = getConnection();
			PreparedStatement statement = con.prepareStatement("SELECT M.title, COUNT(A.aid) as count FROM movies M, acts_in A WHERE A.mid = M.mid GROUP BY M.mid ORDER BY count DESC LIMIT 1");
			ResultSet result = statement.executeQuery();
				
                        swingContainer.createOutputFrame();
			while(result.next()) {
                            String output = result.getString("M.title") + " has the largest cast with " + result.getString("count") + " actors and actresses.";
                            swingContainer.addOutputToGui(output);
                            System.out.println(output);
			}
                        
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			System.out.println("Query 5 complete.");
		}
	}
		
}
