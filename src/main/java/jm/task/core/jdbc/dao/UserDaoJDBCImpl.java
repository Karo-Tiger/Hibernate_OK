package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
     Connection connection = Util.getConnection();
   public void createUsersTable(){
       String sql ="CREATE TABLE IF NOT EXISTS stels (\n" +
                "id INT AUTO_INCREMENT PRIMARY KEY\n" +
                "name VARCHAR(45) ,\n" +
               "lastName VARCHAR(45),\n" +
                "age TINYINT";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            System.out.println("Успешно");
        } catch (SQLException e) {
            System.out.println("Ошибка"+e.getMessage());
            e.printStackTrace();
        }

    }

    public void dropUsersTable(){
        String sql = "DROP TABLE stels";
        try {
            Statement statement = connection.createStatement();
            statement.executeQuery(sql);
            System.out.println("Успешно");
        } catch (SQLException e) {
            System.out.println("Ошибка!"+ e.getMessage());
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        String sql = "INSERT INTO stels (name, lastName, age) values (?,?,?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3,age);
            preparedStatement.execute();
            System.out.println("Успешно");
        } catch (SQLException e) {

                throw new SQLException("Ошибка"+e.getMessage(),e);

        }

    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM stels WHERE id = ? ";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public List<User> getAllUsers() throws SQLException {
        List<User> addUser = new ArrayList<>();
        String sql = "SELECT ID, NAME, LASTNAME,AGE FROM stels";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NANE"));
                user.setLastName(resultSet.getString("LASTNANE"));
                user.setAge(resultSet.getByte("AGE"));
                addUser.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(preparedStatement != null){
                preparedStatement.close();
            }
            if(connection !=null){
                connection.close();
            }
        }

        return addUser;
    }

    public void cleanUsersTable() {

        String sql = "TRUNCATE TABLE stels";
        try {
            Statement statement  = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
