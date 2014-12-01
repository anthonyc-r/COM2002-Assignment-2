package forms;

import java.util.logging.Logger;
import java.sql.*;

public class QueryHandler{
    
    public QueryHandler(String uname, String pwd){
        this.usr = uname;
        this.pwd = pwd;
    }

	public ResultSet executeQueryRS(String query) throws 
    	InstantiationException, IllegalAccessException{
		ResultSet rs = null;
        try {
    		Class.forName("com.mysql.jdbc.Driver").newInstance();
    	} catch (ClassNotFoundException e) {
    		throw new RuntimeException("Can't find driver", e);
    	}
        LOGGER.info("Loaded driver.");
		try {
    		Connection conn = DriverManager.getConnection
    				("jdbc:mysql://stusql.dcs.shef.ac.uk/team016?user=team016&password=eabb6f40");
    		if (conn!=null)
    			System.out.println("Connection successful");

    		Statement stmt = conn.createStatement();
    		LOGGER.info("Created statement");
            rs = stmt.executeQuery(query);
    		LOGGER.info("Got ResultSet");
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
		}

		return rs;
    }

    public String[] executeQuery(String qri){
        ResultSet rs = null;
        LOGGER.info("Getting result set.");
        try{
            rs = executeQueryRS(qri);
        }catch(InstantiationException i){
            throw new RuntimeException("Instantiation ex thrown.", i);
        }catch(IllegalAccessException i){
            throw new RuntimeException("Illegal access ex thrown.", i);
        }
        LOGGER.info("Got result set.");
        try{
            //Execute query and get resset 
            LOGGER.info("Getting meta data.");
            ResultSetMetaData rsmd = rs.getMetaData();
            LOGGER.info("Got meta data");
            int noCols = rsmd.getColumnCount();
            String[] returnStr = new String[noCols];
            LOGGER.info("Data indicates there are "+noCols+" columns.");
            //check if it's empty & ifnot move to first row
            if(!rs.next()){
                //if so return null
                LOGGER.info("Result set empty.");
                return null;
            } 
            else{
                LOGGER.info("Transfering columns to String array.");
                for(int i=1; i<=noCols; i++){
                    //get each column and put it into an array
                    returnStr[i-1] = rs.getString(i);
                }
                LOGGER.info("String array formed.");
                return returnStr;
                //return array
            } 
        }catch(SQLException e){
			System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
		}
        //Something went wrong :^(
        throw new RuntimeException("Something went wrong :^(");
    }

    public int executeUpdate(String updt){
        //Load driver.
        try {
    		Class.forName("com.mysql.jdbc.Driver").newInstance();
    	}catch(ClassNotFoundException e){
    		throw new RuntimeException("Can't find driver", e);
    	}catch(InstantiationException i){
            throw new RuntimeException("Instantiation error thrown.", i);
        }catch(IllegalAccessException i){
            throw new RuntimeException("Illegal access ex thrown.", i);
        }
        //Connect to database.
		try {
    		Connection conn = DriverManager.getConnection("jdbc:mysql:"+
                    "//stusql.dcs.shef.ac.uk/"+usr+"?"+
                    "user="+usr+
                    "&password="+pwd);
            //Check connection success
    		if (conn!=null)
    			System.out.println("Connection successful");

    		Statement stmt = conn.createStatement();
    		//Run the update and get the return
            int updtStatus = stmt.executeUpdate(updt);
            return updtStatus;

		}catch(SQLException e){
			System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
		}
        //Something went wrong... Return non-sensible value
		return -1;
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
    
    public static void main(String[] args){
    }


    private String pwd=null, usr=null;
    private final static Logger LOGGER = Logger.getLogger(
            QueryHandler.class.getName());
}
