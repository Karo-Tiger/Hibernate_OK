package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory sessionFactory = Util.getsf();
    Transaction transaction = null;
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.doWork(connection -> {
                try {
                    connection.prepareStatement("CREATE TABLE IF NOT EXISTS sarym (" +
                            "   id INT PRIMARY KEY AUTO_INCREMENT," +
                            "   name VARCHAR(45)," +
                            "   lastName VARCHAR(45)," +
                            "   age TINYINT" +
                            ")").execute();
                    System.out.println("Таблица успешно создана!");
                } catch (RuntimeException e) {
                    transaction.commit();
                    System.out.println("Ошибка!");
                    throw new RuntimeException(e);
                }
            });
            session.close();
        }

    }

    @Override
    public void dropUsersTable() {
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.doWork(connection -> {
                try {
                    connection.prepareStatement("DROP TABLE IF EXISTS sarym").execute();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            transaction.commit();
            System.out.println("Таблица удалина!");

        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction(); // Начинаем новую транзакцию

            User user = new User(name, lastName, age);
            session.save(user); // Сохраняем объект в базу данных

            tx.commit(); // Фиксируем изменения
            System.out.println("Данные успешно добавлены!");
        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println("Произошла ошибка при сохранении пользователя.");
        }
    }

    @Override
    public void removeUserById(long id) {
       try(Session session = sessionFactory.openSession()) {
           transaction = session.beginTransaction();
           User user = session.find(User.class,id);
           if(user !=null){
               session.delete(user);
               System.out.println("Пользователь с id: "+id+"удален");
           }else {
               System.out.println("Возникла ошибка");
           }
           transaction.commit();

       }

    }

    @Override
    public  List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            list = session.createQuery("From User", User.class).list();
        }catch (HeadlessException e){
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            System.out.println("Данные успешно удалены!");
            transaction.commit();
        }catch (HibernateException e){
            e.printStackTrace();
            System.out.println("Ошибка!");
        }

    }
}
