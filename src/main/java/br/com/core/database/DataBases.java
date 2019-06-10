package br.com.core.database;

import br.com.core.enums.DbPlatform;
import br.com.core.report.ExtentReports;

import java.sql.*;

public class DataBases {

    private String host;
    private String port;
    private String dataBase;
    private String user;
    private String pass;
    private Connection connection;

    public DataBases(String host, String port, String dataBase, String user, String pass) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.pass = pass;
        this.dataBase = dataBase;
    }

    /**
     * Start DB connection using attribute of its object
     */
    public void openConnection(DbPlatform dbPlatform) {
        String url;
        try {

            switch (dbPlatform) {
                case MYSQL:
                    Class.forName("com.mysql.jdbc.Driver");
                    url = "jdbc:mysql://" + host + ":" + port + "/" + dataBase +
                            "?useUnicode=true&useJDBCCompliantTimezoneShift=true" +
                            "&useLegacyDatetimeCode=false&serverTimezone=UTC";
                    setConnection(DriverManager.getConnection(url, user, pass));
                    break;
                case ORACLE:
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    url = "jdbc:oracle:thin:@//" + host + ":" + port + "/" + dataBase;
                    setConnection(DriverManager.getConnection(url, user, pass));
                    break;
                case POSTGRESQL:
                    DriverManager.registerDriver(new org.postgresql.Driver());
                    Class.forName("org.postgresql.Driver");
                    url = "jdbc:postgresql://" + host + ":" + port + "/" + dataBase;
                    setConnection(DriverManager.getConnection(url, user, pass));
                    break;
                default:
            }
        } catch (Exception e) {
            ExtentReports.appendToReport("Error on method 'openConnection': " + e.getMessage());
        }
    }

    /**
     * Close DB connection
     */
    public void closeConnection() {
        try {
            if (!getConnection().isClosed()) {
                getConnection().close();
            }
        } catch (SQLException e) {
            ExtentReports.appendToReport("Error on method 'closeConnection': " + e.getMessage());
        }
    }

    /**
     * Execute SQL commands such as Select, Update and Delete
     *
     * @param sql instruction
     */
    public ResultSet executeSqlCommand(String sql) {
        PreparedStatement ps;
        ResultSet rs = null;
        try {
            ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.execute();
            rs = ps.getGeneratedKeys();
        } catch (SQLException e) {
            ExtentReports.appendToReport("Error on method 'executeSqlCommand': " + e.getMessage());
        }
        return rs;

    }

    /**
     * Execute a Select query into DB
     *
     * @param sql Select - SQL query
     * @return The data from SQL statement
     */
    public ResultSet select(String sql) {
        PreparedStatement ps;
        ResultSet rs = null;
        try {
            ps = getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            ExtentReports.appendToReport("Error on method 'select': " + e.getMessage());
        }

        return rs;
    }

    /**
     * Checks if the specified column contains the expected value
     *
     * @param rs          ResultSet from a SQL Query
     * @param indexColumn index of the column
     * @param contains    If true, checks if column contains the specified text. If false, check exact match
     * @param value       String to be checked on column
     * @return True or False
     */
    public boolean findValueColumn(ResultSet rs, int indexColumn, boolean contains, String value) {
        try {
            while (rs.next()) {
                String name = rs.getString(indexColumn);
                if (contains) {
                    return name.contains(value);
                } else {
                    return name.equals(value);
                }
            }
        } catch (SQLException e) {
            ExtentReports.appendToReport("Error on method 'findValueColumn': " + e.getMessage());
        }
        return false;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
