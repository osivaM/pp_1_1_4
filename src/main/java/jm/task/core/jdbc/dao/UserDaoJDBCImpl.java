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
    public UserDaoJDBCImpl() {}

    public void createUsersTable() {
        Connection connection = null;

        try {
            connection = Util.getConnection();

            try (Statement statement = connection.createStatement()){

                connection.setAutoCommit(false);
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS `pp_1_1_4`.`User` (\n" +
                        "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                        "  `name` VARCHAR(45) NOT NULL,\n" +
                        "  `lastName` VARCHAR(45) NOT NULL,\n" +
                        "  `age` VARCHAR(45) NOT NULL,\n" +
                        "  PRIMARY KEY (`id`));");
                connection.commit();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Ошибка подключения к драйверу.\n" + e.getMessage());
        } catch (SQLException e1) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
            System.out.println("Не удалось создать новую таблицу.\n" + e1.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void dropUsersTable() {
        Connection connection = null;

        try {
            connection = Util.getConnection();

            try (Statement statement = connection.createStatement()) {
                connection.setAutoCommit(false);
                statement.executeUpdate("DROP TABLE IF EXISTS User");
                connection.commit();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Ошибка подключения к драйверу.\n" + e.getMessage());
        } catch (SQLException e1) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
            System.out.println("Не удалось удалить таблицу.\n" + e1.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = null;

        try {
            connection = Util.getConnection();

            try (Statement statement = connection.createStatement()) {
                connection.setAutoCommit(false);
                statement.executeUpdate("INSERT INTO User SET name = '" + name + "', lastName = '" + lastName + "', age = " + age);
                connection.commit();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Ошибка подключения к драйверу.\n" + e.getMessage());
        } catch (SQLException e1) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e2) {
                    System.out.println(e1.getMessage());
                }
            }
            System.out.println("Не удолось добавить запись.\n" + e1.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        System.out.println("User " + name + " добавлен в базу данных.");
    }

    public void removeUserById(long id) {
        Connection connection = null;

        try {
            connection = Util.getConnection();

            try (Statement statement = connection.createStatement()) {
                connection.setAutoCommit(false);
                statement.executeUpdate("DELETE FROM User WHERE id = " + id);
                connection.commit();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Ошибка подключения к драйверу.\n" + e.getMessage());
        } catch (SQLException e1) {
            System.out.println("Не удалось удалить запись.\n" + e1.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        Connection connection = null;

        try {
            connection = Util.getConnection();

            try (Statement statement = connection.createStatement()) {
                connection.setAutoCommit(false);
                ResultSet resultSet = statement.executeQuery("SELECT * FROM User");
                connection.commit();

                while (resultSet.next()) {
                    User user = new User();

                    user.setId(resultSet.getLong("id"));
                    user.setName(resultSet.getString("name"));
                    user.setLastName(resultSet.getString("lastName"));
                    user.setAge((byte) resultSet.getInt("age"));

                    list.add(user);
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Ошибка подключения к драйверу.\n" + e.getMessage());
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
            System.out.println("Не удалось получить все записи.\n" + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return list;
    }

    public void cleanUsersTable() {
        Connection connection = null;

        try {
            connection = Util.getConnection();

            try (Statement statement = connection.createStatement()) {
                connection.setAutoCommit(false);
                statement.executeUpdate("DELETE FROM User");
                connection.commit();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Ошибка подключения к драйверу.\n" + e.getMessage());
        } catch (SQLException e1) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
            System.out.println("Не удалось очистить таблицу.\n" + e1.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
