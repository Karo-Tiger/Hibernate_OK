package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Arnold","Schwarzenegger",(byte) 80);
        userService.saveUser("Sylvester","Stallone",(byte) 78);
        userService.saveUser("Jean-Claude","Van Damme",(byte) 72);
        userService.saveUser("Dolph","Lundgren",(byte) 69);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
