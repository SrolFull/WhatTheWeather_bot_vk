package DB;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

interface IDataBase {
    Logger LOG = LoggerFactory.getLogger(DataBase.class);
    String CON_STR = "jdbc:sqlite:SQLite/subscribers.db";



    default Map<Integer, String> getSubscribers(@NotNull Connection connection) {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT sub_id, city FROM subs");
            Map<Integer,String> products = new HashMap<>();
            while (resultSet.next()) {
                products.put(resultSet.getInt("sub_id"),resultSet.getString("city"));
            }
            return products;

        } catch (SQLException e) {
            LOG.error(String.valueOf(e));
            return new HashMap<>();
        }
    }

    default boolean addSubscriber(@NotNull Connection connection, Integer sub_id, String city) {
        if (isIdExist(connection,sub_id)) {
            return false;
        }
        else{
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO subs(sub_id, city) " +
                            "VALUES(?, ?)")) {
                statement.setInt(1,sub_id);
                statement.setString(2,city);
                statement.execute();
            } catch (SQLException e) {
                LOG.error(String.valueOf(e));
            }
            return true;
        }
    }

    default void deleteSubscriber(@NotNull Connection connection, int user_id) {
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM subs WHERE sub_id = ?")) {
            statement.setInt(1, user_id);
            statement.execute();
        } catch (SQLException e) {
            LOG.error(String.valueOf(e));
        }
    }
    default boolean isIdExist(@NotNull Connection connection, int user_id){
        boolean exist = false;
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT sub_id FROM subs WHERE sub_id = ?")) {
            statement.setInt(1,user_id);
            try (ResultSet resultset = statement.executeQuery()) {
                exist = resultset.next();
            }
        } catch (SQLException e) {
            LOG.error(String.valueOf(e));
        }
        LOG.info(String.valueOf(exist));
        return exist;
    }

}
