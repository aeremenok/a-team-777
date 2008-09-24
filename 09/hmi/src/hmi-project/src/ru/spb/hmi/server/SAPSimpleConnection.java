package ru.spb.hmi.server;


import java.sql.*;

/**
 * User: Павел
 * Date: 26.08.2006
 * Time: 13:13:51
 *
 * Соединение с mySQL базой
 */
public class SAPSimpleConnection implements SAPConnection {

    private Connection conn = null;
    private Statement statement = null;


    /**
     * Конструктор - читет все необходимое из конфига.
     */
    public SAPSimpleConnection() {

        //Читаем необходимые настройки из конфига
        String user = config.getEntryValue("MYSQL_USER");
        String passw = config.getEntryValue("MYSQL_PWD");
        String host = config.getEntryValue("MYSQL_HOST");
        String schema = config.getEntryValue("MYSQL_SCHEMA");

        createConnection(host, schema, user, passw);
    }

    /**
     * Создает соединение с базой с необходимыми настройками соединения
     * Кодировка ставиться по умолчанию - cp1251
     * @param host - к кому подсоединяемся (где стоит база)
     * @param schema - схема использумой базы
     * @param user - имя пользователя доступа к базе
     * @param passw - пароль пользователя к базе
     */
    private void createConnection(String host, String schema, String user, String passw) {
        //connectionString = DriverManager.getConnection("jdbc:mysql://" + host + "/" + schema + "?user=" + user + "&password=" + passw + "&useUnicode=true&characterEncoding=utf8");

        String connectionString = "jdbc:mysql://" + host + "/" + schema +
                    "?user=" + user +
                    "&password=" + passw +
                    "&useUnicode=true" +             //это и след. должны использоваться вместе!!!
                    "&characterEncoding=cp1251";
        createConnection(connectionString, false);
    }

    /**
     * Создает соединение с базой с необходимыми настройками соединения
     * @param host - к кому подсоединяемся (где стоит база)
     * @param schema - схема использумой базы
     * @param user - имя пользователя доступа к базе
     * @param passw - пароль пользователя к базе
     * @param additionalString - дополнительные параметры настройки для драйвера mySQL (начиная с &)
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
     * Создает соединение с базой с необходимыми настройками соединения
     * @param connectionString - строка подсоединения к базе
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
     * Создаем коннекшен на основе уже имеющегося Connection
     * @param conn
     * @throws SQLException
     */
    public SAPSimpleConnection(Connection conn) throws SQLException {
        this.conn = conn;
        this.statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
    }

    /**
     * Получаем текущий коннекшен
     * @return текущий коннекшен
     */
    public Connection getConnection() {
        return conn;
    }

    /**
     * Выполняем mySQL запрос (select)
     *
     * @param query - запрос
     * @return resultSet - результат выполнения запроса к базе
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
     * Выполняем insert или update
     *
     * @param query - сам mySQL запрос
     * @return количество измененных/добавленных строк
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
     * Подтверждаем изменения
     * @throws java.sql.SQLException
     */
    public void commit() throws SQLException {
        conn.commit();
    }

    /**
     * Отменяем изменения
     * @throws java.sql.SQLException
     */
    public void rollback() throws SQLException {
        conn.rollback();
    }

    /**
     * Закрываем коннекшен с коммитом
     * @throws java.sql.SQLException
     */
    public void closeConnection() throws SQLException {
        closeConnection(true);
    }

    /**
     * Закрываем коннекшен
     * @param ifCommit :true - с коммитом false- с роллбеком
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
     * Необходимо сначала закрыть соединение.
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
