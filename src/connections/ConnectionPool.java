package connections;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ConnectionPool {
	
    private  int MAX_CONNECTIONS = 8 ;
    private static ConnectionPool instance = null;  // lazy loading
    private  Set<Connection> usedConnections = new HashSet<Connection>() ;//1,2
    private  ArrayList<Connection> availableConnections = new ArrayList<>();//2.1
    private String dbUrl ;
    private  String user ;
    private  String password ;
    private String driver;
 
    private ConnectionPool() throws SQLException {
    	
    	  driver =  "com.mysql.cj.jdbc.Driver";//prop.getProperty("driver");
    	  dbUrl = "jdbc:mysql://127.0.0.1:3306/coupons_forward_cusotmer?useSSL=false&serverTimezone=GMT%2B8";
    	  user = "root";//prop.getProperty("user");
    	  password = "0549887787"; //prop.getProperty("password");
    	      
    	      for ( int count = 0 ; count<MAX_CONNECTIONS;count++) {
    	    	  availableConnections.add(initializeConnections());
    	      }
    }

    public synchronized static ConnectionPool getInstance() {
        if(instance == null) {
        	try {
        		instance = new ConnectionPool();
            }catch (Exception e) {
				e.printStackTrace();
			}
        }
        return instance;
    }

    private Connection initializeConnections() throws SQLException {
    	try {
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return DriverManager.getConnection(dbUrl,user,password);
    }

    
    public Connection  getConnection(){
    	
    	if(availableConnections.size() == 0) {
    		System.out.println("all the connection in used");
    		return null;
    	}else {
    		
    		Connection connection = availableConnections.remove(availableConnections.size()-1);
    		usedConnections.add(connection);
    		return connection;
    	}
     }
    
    public  void closeAllConnections() {
       for(Connection connection : usedConnections) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
       System.out.println("all the connection are closed");
    }
    
    public  void restoreConnection(Connection connection) {
        if (connection != null) {
          usedConnections.remove(connection);	
          availableConnections.add(connection);
        }
    }
}
