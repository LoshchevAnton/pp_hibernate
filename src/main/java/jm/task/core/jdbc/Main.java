package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        var service = new UserServiceImpl();
        service.createUsersTable();

        User[] users = new User[4];
        users[0] = new User("n1", "ln1", (byte) 10);
        users[1] = new User("n2", "ln2", (byte) 11);
        users[2] = new User("n3", "ln3", (byte) 20);
        users[3] = new User("n4", "ln4", (byte) 27);
        for (User user : users) {
            try {
                service.saveUser(user);
                System.out.println(user);
            } catch (RuntimeException e) {
                System.out.println("Adding user " + user + " failed");
            }
        }

        List<User> addedUsers = service.getAllUsers();
        addedUsers.forEach(System.out::println);

        service.cleanUsersTable();

        service.dropUsersTable();
    }
}
