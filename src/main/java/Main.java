import Pojos.Item;
import Pojos.Transaction;
import Pojos.User;
import Pojos.UserRole;
import dbTools.DbUtil;
import dbTools.SqlConstants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        DbUtil.getINSTANCE().dbConnection();

        User loggedUser = new User();
        int loggedId =-1;
        int loggedRole = -1;

        if(loggedId <0) {

            System.out.println("Give me your email!");
            Scanner scanner = new Scanner(System.in);
            String email = scanner.nextLine();
            System.out.println("Give me your password!");
            String password = scanner.nextLine();

         loggedUser = DbUtil.getINSTANCE().verifying(email,password);
         loggedId= loggedUser.getId();
         loggedRole = DbUtil.getINSTANCE().getRole(loggedId);
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

                    if(loggedRole !=1) {
                        flag =1;
                        System.out.println("You don't have the necessary rights!");
                        break;
                    }

                    User user = new User();
                    System.out.println("Give me your name:");
                    String name = scanner.nextLine();  //1db nextLine nem ál meg a nextInt előtt
                    name = scanner.nextLine();
                    user.setName(name);
                    System.out.println("Give me your password:");
                    String password = scanner.next();
                    user.setPassword(password);
                    System.out.println("Give me your email address:");
                    String email = scanner.next();
                    user.setEmail(email);
                    DbUtil.getINSTANCE().addUserObject(user);


                    System.out.println("Choose a role! Write '1' for admin, or '2' for seller, or '3' for customer");
                    UserRole userRole = new UserRole();
                    userRole.setRoleId( scanner.nextInt());
                    userRole.setUserId(user.getId());
                    if (userRole.getRoleId() < 4 && userRole.getRoleId() > 0) {
                        DbUtil.getINSTANCE().addUserRoleObject(userRole);
                    }
                    else {
                        DbUtil.getINSTANCE().deleteUser(email);
                        flag = 1;
                    }

                    break;

                case 3:

                    if(loggedRole >2) {
                        flag =1;
                        System.out.println("You don't have the necessary rights!");
                        break;
                    }

                    Item item = new Item();
                    System.out.println("Give me the item name:");
                    item.setName( scanner.nextLine());
                    item.setName( scanner.nextLine()); //1db nextLine nem ál meg a nextInt előtt
                    System.out.println("Give me the item price:");
                    item.setPrice( scanner.nextInt());
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

                    if(loggedRole >2) {
                        flag =1;
                        System.out.println("You don't have the necessary rights!");
                        break;
                    }

                    System.out.println("Give me that item's name what are gonna change:");
                    String changeItemName = scanner.nextLine();
                    changeItemName = scanner.nextLine();
                    System.out.println("Give me that item's unit type what are gonna change:");
                    String changeItemUnit = scanner.nextLine();
                    System.out.println("Give me that item's new price:");
                    int changeItemPrice = scanner.nextInt();
                    if(DbUtil.getINSTANCE().getItemId(changeItemName,changeItemUnit)>0) {
                        DbUtil.getINSTANCE().changeItemPrice(changeItemName,changeItemUnit,changeItemPrice);
                    }
                    else {
                        System.out.println("This item is not exist!");
                    }
                    break;
                case 6:
                    Transaction transaction = new Transaction();
                    System.out.println("Give me that item's name what are you want to order:");
                    String orderItemName = scanner.nextLine();
                    orderItemName = scanner.nextLine();
                    System.out.println("Give me that item's unit:");
                    String orderItemUnit = scanner.nextLine();

                    transaction.setItemId(DbUtil.getINSTANCE().getItemId(orderItemName,orderItemUnit));
                    if(transaction.getItemId()==-1) {
                        System.out.println("This item is not exist!");
                        flag = 1;
                        printMenu();
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
