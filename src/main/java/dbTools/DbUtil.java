package dbTools;

import Pojos.Item;
import Pojos.Transaction;
import Pojos.User;
import Pojos.UserRole;

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

    public User verifying(String email, String password) {
        User user = new User();
        user.setId(-1);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlConstants.verifying);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
               user.setId(rs.getInt("id"));
               user.setEmail(rs.getString("email"));
               user.setName(rs.getString("name"));
               user.setPassword(rs.getString("password"));
            }
            if(user.getId()!=-1)
            System.out.println("Sikeres belépés");
        } catch (SQLException e) {
            user.setId(-1);
            System.out.println("Hiba a belépés közben!");
            e.printStackTrace();
        }
        return user;
    }

    public void listAllItems() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlConstants.getALLItems);
            rs = preparedStatement.executeQuery();
            System.out.print( rs.getMetaData().getColumnName(2)+" | ");
            System.out.print( rs.getMetaData().getColumnName(3)+" | ");
            System.out.print( rs.getMetaData().getColumnName(4)+" | ");
            System.out.print( rs.getMetaData().getColumnName(5)+" | ");
            System.out.println( rs.getMetaData().getColumnName(6)+"\n");
            while (rs.next()) {
                System.out.print(rs.getString("name")+" | ");
                System.out.print(rs.getInt("price")+" Ft | ");
                System.out.print(rs.getBoolean("alcoholic")+" | ");
                System.out.print(rs.getInt("quantity")+" | ");
                System.out.print(rs.getString("unit")+" | \n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeItemPrice(String name, String unit, int price) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlConstants.changeItemPrice);
            preparedStatement.setInt(1,price);
            preparedStatement.setString(2,name);
            preparedStatement.setString(3,unit);
            preparedStatement.executeUpdate();
            System.out.println("Sikeres módosítás");
        } catch (SQLException e) {
            System.out.println("Sikertelen módosítás!");
            e.printStackTrace();
        }
    }

    public void newOrder(Transaction transaction) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlConstants.newTransaction);
            preparedStatement.setInt(1,transaction.getItemId());
            preparedStatement.setInt(2,transaction.getQuantity());
            preparedStatement.setString(3,transaction.getDate());
            preparedStatement.setInt(4,transaction.getUserId());
            preparedStatement.executeUpdate();
            System.out.println("Sikeres rendelés!");
        } catch (SQLException e) {
            System.out.println("Sikertelen rendelés!");
            e.printStackTrace();
        }
    }

    public User addUserObject(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlConstants.newUser);
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getPassword());
            preparedStatement.setString(3,user.getEmail());
            preparedStatement.executeUpdate();

            PreparedStatement preparedStatement1 = connection.prepareStatement(SqlConstants.getId);
            preparedStatement1.setString(1,user.getEmail());
            rs = preparedStatement1.executeQuery();
            while (rs.next()) {
                user.setId(rs.getInt("id"));
            }
            System.out.println("Sikeres felhasználó regisztrálás!");
        } catch (SQLException e) {
            System.out.println("Sikertelen felhasználó regisztrálás!");
            e.printStackTrace();
        }

        return user;
    }

    public void addUserRoleObject(UserRole userRole) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlConstants.newRole);
            preparedStatement.setInt(1,userRole.getUserId());
            preparedStatement.setInt(2,userRole.getRoleId());
            preparedStatement.executeUpdate();
            System.out.println("Sikeres role hozzáadás");
        } catch (SQLException e) {
            System.out.println("Sikertelen role hozzáadás!");
            e.printStackTrace();
        }
    }

    public void addItemObject(Item item) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlConstants.addItem);
            preparedStatement.setString(1,item.getName());
            preparedStatement.setInt(2,item.getPrice());
            preparedStatement.setBoolean(3,item.getAlcoholic());
            preparedStatement.setInt(4,item.getQuantity());
            preparedStatement.setString(5,item.getUnit());
            preparedStatement.executeUpdate();
            System.out.println("Sikeres termék hozzáadás!");
        } catch (SQLException e) {
            System.out.println("Sikertelen termék hozzáadás!");
            e.printStackTrace();
        }
    }

    public int getItemId(String name,String unit) {
        int id=-1;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlConstants.getItemId);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,unit);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                id = rs.getInt("item_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public int getRole(int userId) {
        int loggedUserRole =-1;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlConstants.getRole);
            preparedStatement.setInt(1,userId);
            rs = preparedStatement.executeQuery();
            if(rs.next()) {
                loggedUserRole = rs .getInt("role_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loggedUserRole;
    }
}
