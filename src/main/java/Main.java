import Pojos.Item;
import Pojos.Transaction;
import Pojos.User;
import Pojos.UserRole;
import dbTools.DbUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        DbUtil.getINSTANCE().dbConnection();

        int registeredId = -1;

        User loggedUser = new User();
        int loggedId =-1;

        if(loggedId <0) {

            System.out.println("Give me your email!");
            Scanner scanner = new Scanner(System.in);
            String email = scanner.nextLine();
            System.out.println("Give me your password!");
            String password = scanner.nextLine();

         loggedUser = DbUtil.getINSTANCE().verifying(email,password);
         loggedId= loggedUser.getId();
        }

        if (loggedId<0) {
            System.out.println("Wrong password/email!");
        }

        int flag = -1;
        while (flag != 0 && loggedId>0) {

            if (flag != 1) {
                printMenu();
            }
            System.out.println("\n Please choose one from this list!");
            Scanner scanner = new Scanner(System.in);
            flag = scanner.nextInt();

            switch (flag) {
                case 0:
                    break;
                case 1:
                    printMenu();
                    break;
                case 2:
                    /*
                    System.out.println("Give me your name:");
                    String name = scanner.next();
                    System.out.println("Give me your password:");
                    String password = scanner.next();
                    System.out.println("Give me your email address:");
                    String email = scanner.next();
                    DbUtil.getINSTANCE().addUser(name, password, email);

                    System.out.println("Choose a role! Write '1' for admin, or '2' for seller, or '3' for customer");
                    int role = scanner.nextInt();
                    if (role < 4 && role > 0) {
                        DbUtil.getINSTANCE().setRole(DbUtil.getINSTANCE().getUserId(email), role);
                    }
                    else {
                        DbUtil.getINSTANCE().deleteUser(email);
                        flag = 1;
                    }
                    break;
                    */

                    User user = new User();
                    System.out.println("Give me your name:");
                    String name = scanner.next();
                    user.setName(name);
                    System.out.println("Give me your password:");
                    String password = scanner.next();
                    user.setPassword(password);
                    System.out.println("Give me your email address:");
                    String email = scanner.next();
                    user.setEmail(email);
                    DbUtil.getINSTANCE().addUserObject(user);

                    registeredId = user.getId();

                    System.out.println("Choose a role! Write '1' for admin, or '2' for seller, or '3' for customer");
                    UserRole userRole = new UserRole();
                    userRole.setRoleId( scanner.nextInt());
                    userRole.setUserId(registeredId);
                    if (userRole.getRoleId() < 4 && userRole.getRoleId() > 0) {
                        DbUtil.getINSTANCE().addUserRoleObject(userRole);
                    }
                    else {
                        DbUtil.getINSTANCE().deleteUser(email);
                        flag = 1;
                    }

                    break;

                case 3:
                    /*
                    System.out.println("Give me the item name:");
                    String itemName = scanner.next();
                    System.out.println("Give me the item price:");
                    int itemPrice = scanner.nextInt();
                    System.out.println("The item has contains alcohol. True or false?");
                    boolean itemAlcoholic = scanner.nextBoolean();
                    System.out.println("How many item has got?");
                    int itemQuantity = scanner.nextInt();
                    System.out.println("What is the unit of the item?");
                    String itemUnit = scanner.next();
                    DbUtil.getINSTANCE().addItem(itemName,itemPrice,itemAlcoholic,itemQuantity,itemUnit);
                    */

                    Item item = new Item();
                    System.out.println("Give me the item name:");
                    item.setName( scanner.next());
                    System.out.println("Give me the item price:");
                    item.setPrice(scanner.nextInt());
                    System.out.println("The item has contains alcohol. True or false?");
                    item.setAlcoholic(scanner.nextBoolean());
                    System.out.println("How many item has got?");
                    item.setQuantity(scanner.nextInt());
                    System.out.println("What is the unit of the item?");
                    item.setUnit(scanner.next());
                    DbUtil.getINSTANCE().addItemObject(item);
                    break;
                case 4:
                    DbUtil.getINSTANCE().listAllItems();
                    break;
                case 5:
                    System.out.println("Give me that item's name what are gonna change:");
                    String changeItemName = scanner.next();
                    System.out.println("Give me that item's unit type what are gonna change:");
                    String changeItemUnit = scanner.next();
                    System.out.println("Give me that item's new price:");
                    int changeItemPrice = scanner.nextInt();
                    DbUtil.getINSTANCE().changeItemPrice(changeItemName,changeItemUnit,changeItemPrice);
                    break;
                case 6:
                    Transaction transaction = new Transaction();
                    System.out.println("Give me that item's name what are you want to order:");
                    String orderItemName = scanner.next();
                    System.out.println("Give me that item's unit:");
                    String orderItemUnit = scanner.next();
                    transaction.setItemId(DbUtil.getINSTANCE().getItemId(orderItemName,orderItemUnit));
                    if(transaction.getItemId()==-1) {
                        System.out.println("Ilyen termék nincs!");
                        flag = 1;
                        break;
                    }
                    System.out.println("How many item would like to order?");
                    transaction.setQuantity(scanner.nextInt());
                    transaction.setUserId(loggedId);
                    transaction.setDate(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
                    DbUtil.getINSTANCE().newOrder(transaction);
                    break;
                default:
                    printMenu();
                    break;
            }
        }

    }

    public static void printMenu() {
        System.out.println("-------------Kilépés(0)--------------");
        System.out.println("---------------Menü(1)---------------");
        System.out.println("--------Új felhasználó(2)------------");
        System.out.println("--------Új termék felvitel(3)--------");
        System.out.println("-------Termékek kilistázása(4)-------");
        System.out.println("-----------Ár módosítás(5)-----------");
        System.out.println("----Új központi rendelés leadás(6)---");
    }

}
