package forms;

import java.util.logging.Logger;
import java.sql.*;
import java.util.ArrayList;

public class QueryHandler{
    
    public QueryHandler(String uname, String pwd){
        this.usr = uname;
        this.pwd = pwd;
        try {
    		Class.forName("com.mysql.jdbc.Driver").newInstance();
    	} catch (ClassNotFoundException e) {
    		throw new RuntimeException("Can't find driver", e);
    	} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        LOGGER.info("Loaded driver.");
    }
    
	public Object[] executeQueryRS(String query) {
		ResultSet rs = null;
        Connection conn = null;
		try {
    		conn = DriverManager.getConnection
    				("jdbc:mysql://stusql.dcs.shef.ac.uk/"+usr+"?user="+usr+"&password="+pwd);
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
		Object[] connectionObjs = new Object[] {
				conn,
				rs
		};
		return connectionObjs;
    }

    public String[] executeQuery(String qri){
    	Object[] connectionObjs = null;
        ResultSet rs = null;

        LOGGER.info("Getting result set.");
        connectionObjs = executeQueryRS(qri);
		rs = (ResultSet)connectionObjs[1];
        LOGGER.info("Got result set."); 
        String[] returnStr = null;
        try{
            //Execute query and get resset 
            LOGGER.info("Getting meta data.");
            ResultSetMetaData rsmd = rs.getMetaData();
            LOGGER.info("Got meta data");
            int noCols = rsmd.getColumnCount();
            returnStr = new String[noCols];
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
                //return array
            } 
        }catch(SQLException e){
			System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
		} finally {
			try {
				Connection conn = (Connection)connectionObjs[0];
				conn.close();
			} catch (SQLException e) {
				//ignore
			}
		}
        return returnStr;
    }

    public int executeUpdate(String updt){
        //Connect to database.
        int updtStatus = -1;
        Connection conn = null;
		try {
    		conn = DriverManager.getConnection("jdbc:mysql:"+
                    "//stusql.dcs.shef.ac.uk/"+usr+"?"+
                    "user="+usr+
                    "&password="+pwd);
            //Check connection success
    		if (conn!=null)
    			System.out.println("Connection successful");

    		Statement stmt = conn.createStatement();
    		//Run the update and get the return
            updtStatus = stmt.executeUpdate(updt);
		}catch(SQLException e){
			System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				//ignore
			}
    	}
		return updtStatus;
    }


    public String[][] executeQueryFull(String qri){
    	
        Object[] connectionObjs = null;
        ResultSet rs = null;

        int noRows = 0;
        int noCols = 0;

        LOGGER.info("Getting result set.");
        connectionObjs = executeQueryRS(qri);
		rs = (ResultSet)connectionObjs[1];
        LOGGER.info("Got result set."); 
        ArrayList<String[]> AryReturnStr = new ArrayList<String[]>();
        try{
            //Execute query and get resset 
            LOGGER.info("Getting meta data.");
            ResultSetMetaData rsmd = rs.getMetaData();
            LOGGER.info("Got meta data");
            int currentRow = 0;
            noCols = rsmd.getColumnCount();
            LOGGER.info("Data indicates there are "+noCols+" columns.");
            //check if it's empty & ifnot move to first row
            if(!rs.next()){
                //if so return null
                LOGGER.info("Result set empty.");
                return null;
            } 
            do{
                String[] rowStr = new String[noCols];
                LOGGER.info("Transfering columns to String array.");
                for(int i=1; i<=noCols; i++){
                    //get each column and put it into an array
                    rowStr[i-1] = rs.getString(i);
                }
                AryReturnStr.add(rowStr);
                currentRow++;
                LOGGER.info("String array formed.");
            }while(rs.next());
            //return array 
        }catch(SQLException e){
			System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
		} finally {
			try {
				Connection conn = (Connection)connectionObjs[0];
				conn.close();
			} catch (SQLException e) {
				//ignore
			}
		}
        //Can't cast (String[][])..toAray().
        //Need to iterate over it to conver it.
        //return (String[][])AryReturnStr.toArray();
        noRows = AryReturnStr.size();
        String[][] returnStr = new String[noRows][noCols];
        for(int i=0; i<noRows; i++){
            returnStr[i] = AryReturnStr.get(i);
        }

        return returnStr;
    }


	public void closeConn(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if (rs!=null)
				rs.close();
			if (stmt!=null)
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
