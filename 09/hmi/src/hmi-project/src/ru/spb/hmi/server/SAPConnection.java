package ru.spb.hmi.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: Павел
 * Date: 26.08.2006
 * Time: 13:24:41
 *
 * Интерфейс работы с mySQL базой
 */
public interface SAPConnection {

    /**
     * Получаем текущий коннекшен
     * @return текущий коннекшен
     */
    Connection getConnection();

    /**
     * Выполняем mySQL запрос (select)
     * @param query - запрос
     * @return resultSet - результат выполнения запроса к базе
     * @throws SQLException
     */
    ResultSet doMySQLQuery(String query) throws SQLException;

    /**
     * Выполняем insert или update
     * @param query - сам mySQL запрос
     * @return количество измененных/добавленных строк
     * @throws SQLException
     */
    int doMySQLUpdate(String query) throws SQLException;

    /**
     * Подтверждаем изменения
     * @throws SQLException
     */
    void commit() throws SQLException;

    /**
     * Отменяем изменения
     * @throws SQLException
     */
    void rollback() throws SQLException;

    /**
     * Закрываем коннекшен с коммитом
     * @throws SQLException
     */
    void closeConnection() throws SQLException;

    /**
     * Закрываем коннекшен
     * @param ifCommit :true - с коммитом false- с роллбеком
     * @throws SQLException
     */
    void  closeConnection(boolean ifCommit) throws SQLException;
}
