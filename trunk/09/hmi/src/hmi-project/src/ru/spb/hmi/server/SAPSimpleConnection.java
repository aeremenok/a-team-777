package ru.spb.hmi.server;


import java.sql.*;

/**
 * User: �����
 * Date: 26.08.2006
 * Time: 13:13:51
 *
 * ���������� � mySQL �����
 */
public class SAPSimpleConnection implements SAPConnection {

    private Connection conn = null;
    private Statement statement = null;


    /**
     * ����������� - ����� ��� ����������� �� �������.
     */
    public SAPSimpleConnection() {

        //������ ����������� ��������� �� �������
        String user = config.getEntryValue("MYSQL_USER");
        String passw = config.getEntryValue("MYSQL_PWD");
        String host = config.getEntryValue("MYSQL_HOST");
        String schema = config.getEntryValue("MYSQL_SCHEMA");

        createConnection(host, schema, user, passw);
    }

    /**
     * ������� ���������� � ����� � ������������ ����������� ����������
     * ��������� ��������� �� ��������� - cp1251
     * @param host - � ���� �������������� (��� ����� ����)
     * @param schema - ����� ����������� ����
     * @param user - ��� ������������ ������� � ����
     * @param passw - ������ ������������ � ����
     */
    private void createConnection(String host, String schema, String user, String passw) {
        //connectionString = DriverManager.getConnection("jdbc:mysql://" + host + "/" + schema + "?user=" + user + "&password=" + passw + "&useUnicode=true&characterEncoding=utf8");

        String connectionString = "jdbc:mysql://" + host + "/" + schema +
                    "?user=" + user +
                    "&password=" + passw +
                    "&useUnicode=true" +             //��� � ����. ������ �������������� ������!!!
                    "&characterEncoding=cp1251";
        createConnection(connectionString, false);
    }

    /**
     * ������� ���������� � ����� � ������������ ����������� ����������
     * @param host - � ���� �������������� (��� ����� ����)
     * @param schema - ����� ����������� ����
     * @param user - ��� ������������ ������� � ����
     * @param passw - ������ ������������ � ����
     * @param additionalString - �������������� ��������� ��������� ��� �������� mySQL (������� � &)
     */
    private void createConnection(String host, String schema, String user, String passw, String additionalString) {
        //connectionString = DriverManager.getConnection("jdbc:mysql://" + host + "/" + schema + "?user=" + user + "&password=" + passw + "&useUnicode=true&characterEncoding=utf8");

        String connectionString = "jdbc:mysql://" + host + "/" + schema +
                    "?user=" + user +
                    "&password=" + passw +
                    additionalString;
        createConnection(connectionString, false);
    }


    /**
     * ������� ���������� � ����� � ������������ ����������� ����������
     * @param connectionString - ������ ������������� � ����
     * @param autocommit -
     */
    private void createConnection(String connectionString, boolean autocommit) {

        try {
            // The newInstance() call is a work around for some
            // broken Java implementations
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            // handle the error
            System.out.println("Error getting new instance for com.mysql.jdbc.Driver: " + ex.getMessage());
        }

        try {
            conn = DriverManager.getConnection( connectionString );

            conn.setAutoCommit(autocommit);

            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            logging.getLogger().info("INFO: BaseConn: DriverManager got mysql connection! Connection string :" + connectionString);

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("ERROR REGISTERING MySQL driver! Connection string:"+connectionString);
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        }
    }

    /**
     * ������� ��������� �� ������ ��� ���������� Connection
     * @param conn
     * @throws SQLException
     */
    public SAPSimpleConnection(Connection conn) throws SQLException {
        this.conn = conn;
        this.statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
    }

    /**
     * �������� ������� ���������
     * @return ������� ���������
     */
    public Connection getConnection() {
        return conn;
    }

    /**
     * ��������� mySQL ������ (select)
     *
     * @param query - ������
     * @return resultSet - ��������� ���������� ������� � ����
     * @throws java.sql.SQLException
     */
    public ResultSet doMySQLQuery(String query) throws SQLException {
        ResultSet rs;
        try {
            rs = statement.executeQuery(query);
            return rs;
        } catch (SQLException e) {
            throw new SQLException(e.toString() + " SQLString=" + query);
        }
    }

    /**
     * ��������� insert ��� update
     *
     * @param query - ��� mySQL ������
     * @return ���������� ����������/����������� �����
     * @throws java.sql.SQLException
     */
    public int doMySQLUpdate(String query) throws SQLException {
        int i;
        try {
            i = statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new SQLException(e.toString() + "SQLString=" + query);
        }
        return i;
    }

    /**
     * ������������ ���������
     * @throws java.sql.SQLException
     */
    public void commit() throws SQLException {
        conn.commit();
    }

    /**
     * �������� ���������
     * @throws java.sql.SQLException
     */
    public void rollback() throws SQLException {
        conn.rollback();
    }

    /**
     * ��������� ��������� � ��������
     * @throws java.sql.SQLException
     */
    public void closeConnection() throws SQLException {
        closeConnection(true);
    }

    /**
     * ��������� ���������
     * @param ifCommit :true - � �������� false- � ���������
     * @throws java.sql.SQLException
     */
    public synchronized void closeConnection(boolean ifCommit) throws SQLException {
        if (ifCommit) {
            conn.commit();
        } else {
            conn.rollback();
        }

        statement.close();
        conn.close();

        conn = null;
        statement = null;
    }

    /**
     * ���������� ������� ������� ����������.
     * @throws Throwable
     */
    protected void finalize() throws Throwable {
         if (!conn.isClosed()) {
             statement.close();
             conn.close();
         }
         super.finalize();
    }
}
