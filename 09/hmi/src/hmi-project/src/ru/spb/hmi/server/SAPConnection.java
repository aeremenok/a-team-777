package ru.spb.hmi.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: �����
 * Date: 26.08.2006
 * Time: 13:24:41
 *
 * ��������� ������ � mySQL �����
 */
public interface SAPConnection {

    /**
     * �������� ������� ���������
     * @return ������� ���������
     */
    Connection getConnection();

    /**
     * ��������� mySQL ������ (select)
     * @param query - ������
     * @return resultSet - ��������� ���������� ������� � ����
     * @throws SQLException
     */
    ResultSet doMySQLQuery(String query) throws SQLException;

    /**
     * ��������� insert ��� update
     * @param query - ��� mySQL ������
     * @return ���������� ����������/����������� �����
     * @throws SQLException
     */
    int doMySQLUpdate(String query) throws SQLException;

    /**
     * ������������ ���������
     * @throws SQLException
     */
    void commit() throws SQLException;

    /**
     * �������� ���������
     * @throws SQLException
     */
    void rollback() throws SQLException;

    /**
     * ��������� ��������� � ��������
     * @throws SQLException
     */
    void closeConnection() throws SQLException;

    /**
     * ��������� ���������
     * @param ifCommit :true - � �������� false- � ���������
     * @throws SQLException
     */
    void  closeConnection(boolean ifCommit) throws SQLException;
}
