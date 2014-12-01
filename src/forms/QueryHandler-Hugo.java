import java.sql.*;

public class QueryHandler{

	public ResultSet executeQuery(String query) throws 
    	InstantiationException, IllegalAccessException{
		try {
    		Class.forName("com.mysql.jdbc.Driver").newInstance();
    	} catch (ClassNotFoundException e) {
    		throw new RuntimeException("Can't find driver", e);
    	}
		try {
    		Connection conn = DriverManager.getConnection
    				("jdbc:mysql://stusql.dcs.shef.ac.uk/?user=team016&password=eabb6f40");
    		if (conn!=null)
    			System.out.println("Connection successful"); //Test for connection object
    		Statement stmt = conn.createStatement();
    		ResultSet rs = null;
    		if (stmt.execute(query))
    			return rs;
    		System.out.println("noRS"); //Test for result set object
    		return null;
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
		}

		return null;
    }

	public void closeConn(Connection conn, Statement stmt, ResultSet rs) {
		try {
            rs.close();
            stmt.close();
            conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
		}
    }
    
    public static void main (String[] args) throws 
    	InstantiationException, IllegalAccessException {
    }
}
