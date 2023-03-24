package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        List<User> list;

        userService.createUsersTable();

        userService.saveUser("Petr", "Petrov", (byte) 20);
        userService.saveUser("Nikolay", "Nikolaev", (byte) 21);
        userService.saveUser("Semen", "Semenov", (byte) 22);
        userService.saveUser("Viktor", "Viktorov", (byte) 23);

        list = userService.getAllUsers();
        for (User l : list) {
            System.out.println(l.toString());
        }

        userService.cleanUsersTable();

        userService.dropUsersTable();

        userService.connectionClose();
    }
}
