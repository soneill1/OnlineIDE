package edu.neu.cs5200.s3.onlineide.applications;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
* single point of maintence with db
* crud operations - as opposed to putting access anywhere, good design pattern 
* DAO = data access object 
*/

public class ApplicationDAO {
	
	// imported one that can use different db
	public Connection getConnection() {
		
		String connectionUrl = "jdbc:mysql://localhost:3306/onlineide";
		Connection connection = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(connectionUrl, "root", "admin");
;		} catch (InstantiationException e) {

			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return connection;
	}
	
	public void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// nice api that allows me to forget that im using any particular db
	public void create(Application application) {
		String sql = "insert into applications (name, price) values (?, ?)";
		
		Connection connection = getConnection();
		
		//request magical word in jsp represents the url, the ip, the port, the query string, name value parameters
		
		// will take care of sql injection for us, and make sure 
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, application.getName());
			statement.setDouble(2, application.getPrice());
			statement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// no matter what if exceptions get thrown, i want to get gaurentee this gets executed
			closeConnection(connection);
		}
		
	}
	
	public List<Application> selectAll() {
		List<Application> applications = new ArrayList<Application>();
		
		String sql = "select * from applications";
		
		Connection connection = getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			// returns a resultset
			// 4 records in my case
			ResultSet results = statement.executeQuery();
			// next goes to next iteration and returns true if there
			// what if theres 1000's of record, result set is smart enough to give a subset
			while(results.next()){
				int id = results.getInt("id");
				String name = results.getString("name");
				double price = results.getDouble("Price");
				Application application = new Application(id, name, price);
				applications.add(application);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		
		return applications;
	}
	
	public void remove(int id) {
		String sql = "delete from applications where id=?";
		Connection connection = getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		
		
		
	}
	
	public Application selectOne(int id) {
		Application app = null;
		
		String sql = "select * from applications where id=?";
		
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			// should one have one result
			ResultSet results = statement.executeQuery();
			if(results.next()){
				id = results.getInt("id");
				String name = results.getString("name");
				double price = results.getDouble("price");
				app = new Application(id, name, price);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		
		
		return app;
	}
	
	public void update(int id, Application app) {
		String sql = "update applications set name=?, price=? where id=?";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, app.getName());
			statement.setDouble(2, app.getPrice());
			statement.setInt(3, id);
			statement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}

	public static void main(String[] args) {
		ApplicationDAO dao = new ApplicationDAO();
		
		Application app1 = new Application("Contact List", 3.99);
		dao.create(app1);
		
		
//		Connection connection = dao.getConnection();
//		
//		System.out.println(connection);
//		
//		dao.closeConnection(connection);
	}

}
