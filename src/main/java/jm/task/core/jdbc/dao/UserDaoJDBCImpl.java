package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection;
    private Statement statement;

    public UserDaoJDBCImpl() {
        try {
            connection = Util.getConnection();
            statement = connection.createStatement();
        } catch (ClassNotFoundException e) {
            System.out.println("Ошибка подключение к драйверу.\n" + e.getMessage());
        } catch (SQLException e1) {
            System.out.println("Ошибка подключения к БД.\n" + e1.getMessage());
        }
    }

    public void createUsersTable() {
        try {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `pp_1_1_4`.`User` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NOT NULL,\n" +
                    "  `lastName` VARCHAR(45) NOT NULL,\n" +
                    "  `age` VARCHAR(45) NOT NULL,\n" +
                    "  PRIMARY KEY (`id`));");
        } catch (SQLException e) {
            System.out.println("Не удалось создать новую таблицу.\n" + e.getMessage());
        }
    }

    public void dropUsersTable() {
        try {
            statement.executeUpdate("DROP TABLE IF EXISTS User");
        } catch (SQLException e) {
            System.out.println("Не удалось удалить таблицу.\n" + e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            statement.executeUpdate("INSERT INTO User SET name = '" + name + "', lastName = '" + lastName + "', age = " + age);
        } catch (SQLException e) {
            System.out.println("Не удолось добавить запись.\n" + e.getMessage());
        }
        System.out.println("User " + name + " добавлен в базу данных.");
    }

    public void removeUserById(long id) {
        try {
            statement.executeUpdate("DELETE FROM User WHERE id = " + id);
        } catch (SQLException e) {
            System.out.println("Не удалось удалить запись.\n" + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();

        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM User");

            while (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge((byte) resultSet.getInt("age"));

                list.add(user);
            }

        } catch (SQLException e) {
            System.out.println("Не удалось получить все записи.\n" + e.getMessage());
        }

        return list;
    }

    public void cleanUsersTable() {
        try {
            statement.executeUpdate("DELETE FROM User");
        } catch (SQLException e) {
            System.out.println("Не удалось очистить таблицу.\n" + e.getMessage());
        }
    }

    public void connectionClose() {
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Ошибка закрытия коннектора.\n" + e.getMessage());
        }
    }
}
