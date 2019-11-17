package DB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlite.JDBC;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class db {
    private static final Logger log = LoggerFactory.getLogger(db.class);
    private static final String CON_STR = "jdbc:sqlite:SQLite/subscribers.db";
    private static db instance = null;
    private Connection connection;

    public static synchronized db getInstance() throws SQLException {
        if (instance == null)
            instance = new db();
        return instance;
    }

    private db() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection(CON_STR);
    }

    public Map<Integer, String> getSubscribers() {
        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT sub_id, city FROM subs");
            Map<Integer,String> products = new HashMap<>();
            while (resultSet.next()) {
                products.put(resultSet.getInt("sub_id"),resultSet.getString("city"));
            }
            return products;

        } catch (SQLException e) {
            log.error(String.valueOf(e));
            return new HashMap<>();
        }
    }

    public void addSubcriber(Integer sub_id, String city) {
        if (!isIdExist(sub_id)) {
            try (PreparedStatement statement = this.connection.prepareStatement(
                    "INSERT INTO subs(sub_id, city) " +
                            "VALUES(?, ?)")) {
                statement.setInt(1,sub_id);
                statement.setString(2,city);
                statement.execute();
            } catch (SQLException e) {
                log.error(String.valueOf(e));
            }
        }
    }

    public void deleteSubscriber(int user_id) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "DELETE FROM subs WHERE sub_id = ?")) {
            statement.setInt(1, user_id);
            statement.execute();
        } catch (SQLException e) {
            log.error(String.valueOf(e));
        }
    }


    private boolean isIdExist(int user_id){
        boolean exist = false;
        try (PreparedStatement statement = this.connection.prepareStatement(
                "SELECT sub_id FROM subs WHERE sub_id = ?")) {
                statement.setInt(1,user_id);
                try (ResultSet resultset = statement.executeQuery()) {
                    exist = resultset.next();
                }
            } catch (SQLException e) {
            log.error(String.valueOf(e));
        }
        log.info(String.valueOf(exist));
        return exist;
    }
}
