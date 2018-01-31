package dbTools;

import java.sql.*;

public class DbUtil {

    private static final String URL = "jdbc:postgresql://localhost:5432/storage";
    private static final String USER = "postgres";
    private static final String PASSWORD = "post";
    private static DbUtil INSTANCE;      //singleton design pattern

    Connection connection = null;
    ResultSet rs = null;

    private DbUtil() {

    }

    public Connection dbConnection() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
            System.out.println("Sikeres csatlakozás!");
        } catch (SQLException e) {
            System.out.println("Sikertelen csatlakozás!");
            e.printStackTrace();
        }
        return connection;
    }

    //Singleton design pattern
    public static DbUtil getINSTANCE() {
        if (INSTANCE==null) {
            INSTANCE = new DbUtil();
        }
        return INSTANCE;
    }

    public void addUser(String name, String password, String email) {

        try {
            PreparedStatement preparedStatement = dbConnection().prepareStatement(SqlConstants.newUser);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,password);
            preparedStatement.setString(3,email);
            preparedStatement.execute();

            System.out.println("Sikeres felhasználó regisztrálás!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Sikertelen resisztráció!");
        }

    }

    public void setRole(int userId,int roleId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlConstants.newRole);
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,roleId);
            preparedStatement.executeUpdate();
            System.out.println("Sikeres role hozzáadás!");
        } catch (SQLException e) {
            System.out.println("Sikertelen role hozzáadás!");
            e.printStackTrace();
        }
    }

    public int getUserId(String email) {
        int id=0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlConstants.getId);
            preparedStatement.setString(1,email);
           rs = preparedStatement.executeQuery();
           if (rs.next()) {
               id = rs.getInt("id");
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public void deleteUser(String email) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlConstants.deleteUser);
            preparedStatement.setString(1,email);
            preparedStatement.executeUpdate();
            System.out.println("Sikeres törlés!");
        } catch (SQLException e) {
            System.out.println("Sikertelen törlés!");
            e.printStackTrace();
        }
    }

}
