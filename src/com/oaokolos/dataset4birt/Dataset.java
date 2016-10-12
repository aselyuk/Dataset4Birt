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
    
    static final private int NAVIGATION_NONE        = 0;
    static final private int NAVIGATION_FIRST       = 1;
    static final private int NAVIGATION_LAST        = 2;
    static final private int NAVIGATION_NEXT        = 3;
    static final private int NAVIGATION_PREV        = 4;
    static final private int NAVIGATION_IS_FIRST    = 5;
    static final private int NAVIGATION_IS_LAST     = 6;
    static final private int NAVIGATION_IS_CLOSE    = 7;
    
    private boolean selfConnected = false;
    private Connection connection = null;
    private Driver driver = null;
    
    private SimpleDateFormat dateFormatter = null;
    
    private HashMap<String, String> params;
    
    private ResultSet resultSet = null;
    private ResultSetMetaData resultSetMetadata = null;
    private long resultCount = 0;

    public Dataset(Connection connection) throws Exception {
        if (connection != null) {
            this.connection = connection;
            this.init();
        } else {
            Exception error = new Exception("No connection passed");
            throw error;
        }
    }
    
    // connection string: jdbc:extendedsystems:advantage://srv-a10:6262/y$/fabius/reflis/dict.add
    public Dataset(String connectionString, String user, String password) {
        try {
            this.driver = (Driver) Class.forName(Dataset.DRIVER_NAME).newInstance();
            System.out.println("Got driver");
            this.connection = DriverManager.getConnection(connectionString, user, password);
            System.out.println("Connection establish");
            
            this.selfConnected = true;
            this.init();
        } catch (ClassNotFoundException error) {
            System.out.println("ERROR! Can't load driver");
            Logger.getLogger(Dataset.class.getName()).log(Level.SEVERE, error.getMessage());
        } catch (InstantiationException | IllegalAccessException | SQLException error) {
            System.out.println("ERROR! Can't establish connection");
            Logger.getLogger(Dataset.class.getName()).log(Level.SEVERE, error.getMessage());
        }
    }
    
    private void init() {
        this.dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        this.params = new HashMap<String, String>();
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
                    System.out.println("ERROR! Bad direction param: " + direction);
            }
        } catch (SQLException error) {
            Logger.getLogger(Dataset.class.getName()).log(Level.SEVERE, null, error);
            System.out.println("ERROR! Failed manipulate with result set. Direction: " + direction);
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
                Logger.getLogger(Dataset.class.getName()).log(Level.SEVERE, null, error);
                System.out.println("ERROR! " + message);

                throw error;
            }
        }
    }
    
    public boolean select(String sql) {
        this.resultCount = 0;
        String query = this.prepareQuery(sql);
        try {
            Statement statement = this.connection.createStatement();
            this.resultSet = statement.executeQuery(query);
            
            this.resultSet.last();
            this.resultCount = this.resultSet.getRow();
            this.resultSet.beforeFirst();
            
            this.resultSetMetadata = this.resultSet.getMetaData();
        } catch (Exception error) {
            Logger.getLogger(Dataset.class.getName()).log(Level.SEVERE, null, error);
            System.out.println("ERROR! Failed to run query: " + query);

            return false;
        }
        
        return true;
    }
    
    public boolean execute(String sql) {
        this.resultCount = 0;
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
                Logger.getLogger(Dataset.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("ERROR! Failed to close ResultSet: " + ex.getMessage());
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
    
    public Field field(String fieldName) {
        Object result = null;
        int fieldIndex = this.resultSet.findColumn(fieldName);
        
        return new Field(this.resultSetMetadata.getColumnType(fieldIndex), this.resultSet.getObject(fieldIndex));
    }
}
