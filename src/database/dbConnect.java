package database;

import java.sql.*;
import java.util.ArrayList;

import Model.databaseModel;

public class dbConnect {

	Connection con;
	

	
	
	public void dbconnection() throws Exception {

		String url = "jdbc:mysql://localhost:3306/credentialsaviour";
		String username = "root";
		String password = "root";

		Class.forName("com.mysql.cj.jdbc.Driver");

		con = DriverManager.getConnection(url, username, password);
	}

	public void executingNewUser(String website, String username, String password) throws Exception {

		dbconnection();
		
		String query = "insert into credential (website,username,password) value (?,?,?)";
		PreparedStatement st = con.prepareStatement(query);
		st.setString(1, website);
		st.setString(2, username);
		st.setString(3, password);
		
		int count = st.executeUpdate();
		
		System.out.print(count);

		st.close();
		con.close();
	}

	public ArrayList<databaseModel> gettingResult(String query) throws Exception {
		
		ArrayList<databaseModel> dbmlist = new ArrayList<databaseModel>();
		
		dbconnection();
		
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		
		databaseModel dbModel;
		
		while(rs.next()) {
			dbModel = new databaseModel(
		 rs.getString("website"), 
		 rs.getString("username"),
		 rs.getString("password"));
			dbmlist.add(dbModel);
		}
		
		st.close();
		con.close();
		return dbmlist;
	}
	
	public void deleteEntry(String username, String website) throws Exception {
		
		dbconnection();
		
		String query = "delete from credential WHERE website = ? AND username = ?";
		PreparedStatement st = con.prepareStatement(query);
		st.setString(1, website);
		st.setString(2, username);
		
		int count = st.executeUpdate();
		
		System.out.print(count);

		st.close();
		con.close();
		
	}

}
