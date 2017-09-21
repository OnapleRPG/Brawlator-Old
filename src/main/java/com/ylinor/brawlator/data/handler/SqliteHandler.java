package com.ylinor.brawlator.data.handler;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;
import com.ylinor.brawlator.data.beans.EffectBean;
import com.ylinor.brawlator.data.beans.MonsterBean;
import com.ylinor.brawlator.data.beans.TestBean;
import com.ylinor.brawlator.data.dao.EffectDAO;
import com.ylinor.brawlator.data.dao.MonsterDAO;
import org.spongepowered.api.entity.living.monster.Monster;


import javax.inject.Inject;
import java.sql.*;
import java.util.logging.Logger;
@DatabaseTable(tableName = "system")
public class SqliteHandler {
    @Inject
    private static Logger logger;

    public static final String NAME = "brawlator.db";

    public static final String URL = "jdbc:sqlite:" + NAME;

    public static final int VERSION = 1;


    public static void testConnection() {
        Connection c = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(URL);
        } catch (Exception e) {
            System.out.print(e.getClass().getName() + ": " + e.getMessage());

        }
        System.out.print("Opened database successfully");
    }


    private static Connection connection = null;


    public static Connection getConnection() {
        if (connection == null) {
            createConnexion();
            createDatabase();
        }
        return connection;
    }

    public static void checkVersion() {
        try {

            ResultSet resultSet = connection.createStatement().executeQuery("PRAGMA user_version");
            if (resultSet.getInt("user_version") < VERSION) {
                updateVersion();
                System.out.println("update");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
           /* try {
                connection.close();
            } catch (Exception e){
                e.printStackTrace();
            }*/
        }

    }

    public static void updateVersion() {
        createDatabase();
        try {

            Statement statement = connection.createStatement();
            statement.execute("PRAGMA user_version = " + VERSION);


        } catch (Exception e) {
            e.printStackTrace();
        } /*finally {
            try {
                connection.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }*/


    }

   private static ConnectionSource connectionSource = null;

    private static Connection createConnexion() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(URL);
            connectionSource = new JdbcConnectionSource(URL);

            System.out.println("connexion");
            return connection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void createDatabase() {
        try {
           /* Statement stmt = connection.createStatement();

            stmt.execute(MonsterDAO.tableCreation);
            stmt.execute(EffectDAO.tableCreation);*/

                // create our data-source for the database

                TableUtils.createTableIfNotExists(connectionSource, MonsterBean.class);
                TableUtils.createTableIfNotExists(connectionSource, EffectBean.class);

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

