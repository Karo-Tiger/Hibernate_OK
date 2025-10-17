package jm.task.core.jdbc.service;
import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoHibernateImpl();
    UserDaoHibernateImpl userDaoHibernate = new UserDaoHibernateImpl();

    public void createUsersTable() {

        try {
            userDao.createUsersTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        userDao.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {


        try {
            userDao.saveUser(name,lastName,age);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void removeUserById(long id) {


        try {
            userDao.removeUserById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<User> getAllUsers() {


        try {
            return userDao.getAllUsers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void cleanUsersTable() {

        userDao.cleanUsersTable();
    }
}