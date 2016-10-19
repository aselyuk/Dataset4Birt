package com.oaokolos.dataset4birt;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.*;

/**
 *
 * @author rkievskiy
 */
public class Dataset {
    static final private String DRIVER_NAME = "com.extendedsystems.jdbc.advantage.ADSDriver";

	private static final int NAVIGATION_NONE        = 0;
    private static final int NAVIGATION_FIRST       = 1;
    private static final int NAVIGATION_LAST        = 2;
    private static final int NAVIGATION_NEXT        = 3;
    private static final int NAVIGATION_PREV        = 4;
    private static final int NAVIGATION_IS_FIRST    = 5;
    private static final int NAVIGATION_IS_LAST     = 6;
    private static final int NAVIGATION_IS_CLOSE    = 7;
    
    private boolean selfConnected = false;
    private Connection connection = null;
    private Driver driver = null;
	
	private String connectionString = "";
	private String userName = "";
	private String userPassword = "";
	private String driverName = "";
    
    private SimpleDateFormat dateFormatter = null;
    
    private HashMap<String, String> params;
    
    private ResultSet resultSet = null;
    private ResultSetMetaData resultSetMetadata = null;
	private Log log = null;
    private long resultCount = 0;

    public Dataset(String connectionString, String user, String password) {
        this.init(connectionString, user, password, this.DRIVER_NAME);
    }
	
    public Dataset(String connectionString, String user, String password, String driverName) {
        this.init(connectionString, user, password, driverName);
    }
	
	private void init(String connectionString, String user, String password, String driverName) {
		this.driverName = driverName;
		this.connectionString = connectionString;
		this.userName = user;
		this.userPassword = password;
		
        this.dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        this.params = new HashMap<String, String>();
	}
	
	public void setLog(Log log) {
		this.log = log;
	}
	
	private void log(String message) {
		if (this.log != null) {
			this.log.debug(message);
		} else {
			System.out.println(message);
            Logger.getLogger(Dataset.class.getName()).log(Level.SEVERE, message);
		}
	}
    
    public boolean connect() {
		try {
            this.driver = (Driver) Class.forName(this.driverName).newInstance();
			this.log("Got driver");
            this.connection = DriverManager.getConnection(this.connectionString, this.userName, this.userPassword);
			this.log("Connection establish");
            
            this.selfConnected = true;
        } catch (ClassNotFoundException error) {
			this.log("ERROR! Can't load driver: " + error.getMessage());
			return false;
        } catch (InstantiationException | IllegalAccessException | SQLException error) {
			this.log("ERROR! Can't establish connection: " + error.getMessage());
			return false;
        }
		
		return true;
    }
     
    private String prepareQuery(String sql) {
        for (Map.Entry<String, String> param : params.entrySet()) {
            sql = sql.replace(param.getKey(), param.getValue());
        }
        
        return sql;
    }
    
    private boolean navigation(int direction) {
        boolean result = false;

        try {
            switch (direction) {
                case NAVIGATION_FIRST:
                    result = this.resultSet.first();
                    break;
                case NAVIGATION_LAST:
                    result = this.resultSet.last();
                    break;
                case NAVIGATION_NEXT:
                    result = this.resultSet.next();
                    break;
                case NAVIGATION_PREV:
                    result = this.resultSet.previous();
                    break;
                case NAVIGATION_IS_FIRST:
                    result = this.resultSet.isFirst();
                    break;
                case NAVIGATION_IS_LAST:
                    result = this.resultSet.isLast();
                    break;
                case NAVIGATION_IS_CLOSE:
                    result = this.resultSet.isClosed();
                    break;
                default:
					this.log("ERROR! Bad direction param: " + direction);
            }
        } catch (SQLException error) {
			this.log("ERROR! Failed manipulate with result set. Direction: " + direction + "\n" + error.getMessage());
        }
    
        return result;
    }
    
    public void clearParams() {
        this.params.clear();
    }
    
    public void setParam(String key, Object val) throws Exception {
        if (!this.params.containsKey(key)) {
            if (val instanceof Integer) {
                this.params.put(key, ((Integer) val).toString());
            } else if (val instanceof Double) {
                this.params.put(key, ((Double) val).toString().replace(",", "."));
            } else if (val instanceof Float) {
                this.params.put(key, ((Float) val).toString().replace(",", "."));
            } else if (val instanceof String) {
                this.params.put(key, (String) val);
            } else if (val instanceof Date) {
                this.params.put(key, this.dateFormatter.format((Date) val));
            } else {
                String message = "Bad paramater type: " + val.getClass().getName();
                Exception error = new Exception(message);

				this.log("ERROR! " + message + "\n" + error.getMessage());

				throw error;
            }
        }
    }
    
    public boolean select(String sql) {
        this.clearResult();
        String query = this.prepareQuery(sql);
        try {
            Statement statement = this.connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            this.resultSet = statement.executeQuery(query);
            
            this.resultSet.last();
            this.resultCount = this.resultSet.getRow();
			this.resultSet.beforeFirst();
            
            this.resultSetMetadata = this.resultSet.getMetaData();
        } catch (Exception error) {
			this.log("ERROR! Failed to run query: " + query + "\n" + error.getMessage());

            return false;
        }
        
        return true;
    }
    
    public boolean execute(String sql) {
        this.clearResult();
        Statement statement = null;
        
        String query = this.prepareQuery(sql);
        try {
            statement = this.connection.createStatement();
            this.resultCount = statement.executeUpdate(query);
        } catch (Exception error) {
            Logger.getLogger(Dataset.class.getName()).log(Level.SEVERE, null, error);
            System.out.println("ERROR! Failed to run query: " + query);

            return false;
        }
        
        return true;
    }

    public void clearResult() {
        this.resultCount = 0;
        
        if (this.resultSet != null) {
            try {
                this.resultSet.close();
                this.resultSetMetadata = null;
            } catch (SQLException ex) {
				this.log("ERROR! Failed to close ResultSet: " + ex.getMessage());
            }
            this.resultSet = null;
        }
    }
    
    public void clear() {
        this.clearParams();
        this.clearResult();
    }

    public boolean first() {
        return this.navigation(NAVIGATION_FIRST);
    }

    public boolean last() {
        return this.navigation(NAVIGATION_LAST);
    }
    
    public boolean next() {
        return this.navigation(NAVIGATION_NEXT);
    }
    
    public boolean prev() {
        return this.navigation(NAVIGATION_PREV);
    }
    
    public boolean isFirst() {
        return this.navigation(NAVIGATION_IS_FIRST);
    }
    
    public boolean isLast() {
        return this.navigation(NAVIGATION_IS_LAST);
    }
    
    public boolean isClose() {
        return this.navigation(NAVIGATION_IS_CLOSE);
    }

    public long rowsCount() {
        return this.resultCount;
    }

    public void close() throws SQLException {
        this.clearResult();
            
        if (this.selfConnected) {
            this.connection.close();
        }
    }
    
    public Field field(String fieldName) throws SQLException {
        Object result = null;
        int fieldIndex = this.resultSet.findColumn(fieldName);
        
        return new Field(this.resultSetMetadata.getColumnType(fieldIndex), this.resultSet.getObject(fieldIndex));
    }
}
