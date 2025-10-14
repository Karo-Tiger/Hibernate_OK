package jm.task.core.jdbc.util;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/sportsmen";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Gavarski23";

    // реализуйте настройку соеденения с БД
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("YES");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("NO");
        }
        return connection;
    }
    public static SessionFactory getsf(){
       ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
       SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
       return sf;
    }
}