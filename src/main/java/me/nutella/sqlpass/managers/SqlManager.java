package me.nutella.sqlpass.managers;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.nutella.sqlpass.clients.Client;
import me.nutella.sqlpass.utils.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SqlManager {

    public static SqlManager instance;

    public ExecutorService sqlService;

    private HikariDataSource hikari;

    public SqlManager() {
        instance = this;
        try {
            hikari = new HikariDataSource(new HikariConfig(SqlPass.instance.mysqlProperties.getAbsolutePath()));
        } catch (Exception e) {
            Utils.sendError("Could not Connect To Database. (Information may be invalid)", e.getStackTrace(), e.getCause().getMessage());
        }
        sqlService = Executors.newCachedThreadPool(new ThreadFactoryBuilder().setDaemon(true).setNameFormat("SQL Thread - #%d").build());
    }

    public void submitToDatabase(String statement, Client client) {

        Connection connection = getConnection();
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.executeUpdate();
            Utils.sendInfo("CLIENT #" + client.clientID + " -> " + statement);
        } catch (SQLException e) {
            Utils.sendError("Client #" + client.clientID + ": Could not execute statement.", e.getStackTrace(), e.getCause().getMessage());
        }

    }

    public Connection getConnection() {

        Connection connection = null;

        try {
            connection = hikari.getConnection();
        } catch (SQLException e) {
            Utils.sendError("Could not get hikari connection.", e.getStackTrace(), e.getCause().getMessage());
        }

        return connection;

    }

}
