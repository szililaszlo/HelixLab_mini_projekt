package dbTools;

public class SqlConstants {

    public final static String newUser = "INSERT INTO users (name,password,email) VALUES(?,?,?)";
    public final static String newRole = "INSERT INTO user_roles (user_id,role_id) VALUES(?,?)";
    public final static String getId = "SELECT id FROM users WHERE email=?";
    public final static String deleteUser = "DELETE FROM users WHERE email=?";
}
