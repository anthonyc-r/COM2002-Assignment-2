package forms;


import java.sql.*;

public class QueryHandler{
    
    public QueryHandler(String uname, String pwd){
        this.usr = uname;
        this.pwd = pwd;
    }

	public ResultSet executeQueryRS(String query) throws 
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

    public String[] executeQuery(String qri){
        ResultSet rs = null;
        try{
            rs = executeQueryRS(qri);
        }catch(InstantiationException i){
            throw new RuntimeException("Instantiation ex thrown.", i);
        }catch(IllegalAccessException i){
            throw new RuntimeException("Illegal access ex thrown.", i);
        }
        
        try{
            //Execute query and get resset 
            ResultSetMetaData rsmd = rs.getMetaData();
            int noCols = rsmd.getColumnCount();
            String[] returnStr = new String[noCols];
            //check if it's empty & ifnot move to first row
            if(!rs.next()){
                //if so return null
                return null;
            } 
            else{
                for(int i=0; i<noCols; i++){
                    //get each column and put it into an array
                    returnStr[i] = rs.getString(i);
                }
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
                    "//stusql.dcs.shef.ac.uk/?"+
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
}
