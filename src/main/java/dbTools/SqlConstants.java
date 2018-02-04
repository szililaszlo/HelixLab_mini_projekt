package dbTools;

public class SqlConstants {

    public final static String newUser = "INSERT INTO users (name,password,email) VALUES(?,crypt(?,gen_salt('md5')),?)";
    public final static String newRole = "INSERT INTO user_roles (user_id,role_id) VALUES(?,?)";
    public final static String getId = "SELECT id FROM users WHERE email=?";
    public final static String getItemId = "SELECT item_id FROM items WHERE name=? AND unit =?";
    public final static String deleteUser = "DELETE FROM users WHERE email=?";
    public final static String verifying = "SELECT * FROM users WHERE password = crypt(?, password) AND email = ?";
    public final static String addItem = "INSERT INTO items (name,price,alcoholic,quantity,unit) VALUES(?,?,?,?,?)";
    public final static String getALLItems = "SELECT * FROM items";
    public final static String changeItemPrice = "UPDATE items SET price =? WHERE item_id = (SELECT item_id FROM items WHERE name = ? AND unit = ?)";
    public final static String newOrder = "INSERT INTO transactions (item_id,quantity,transaction_type,transaction_date,user_id,finished) \n" +
            "VALUES ((SELECT item_id FROM items WHERE name = ? AND unit = ?),?,'order',?,?,false)";
    public final static String newTransaction = "INSERT INTO TRANSACTIONS (item_id,quantity,transaction_type,transaction_date,user_id,finished)" +
            "VALUES(?,?,'order',?,?,false)";
    public final static String getRole = "SELECT role_id FROM user_roles WHERE user_id = ?";
}
