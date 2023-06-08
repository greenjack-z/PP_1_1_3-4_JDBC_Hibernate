package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService service = new UserServiceImpl();
        service.createUsersTable();
        service.saveUser("first", "first", (byte) 11);
        service.saveUser("second", "second", (byte) 22);
        service.saveUser("third", "third", (byte) 33);
        service.saveUser("fourth", "fourth", (byte) 44);
        service.getAllUsers().forEach(System.out::println);
        service.cleanUsersTable();
        service.dropUsersTable();
    }
}
