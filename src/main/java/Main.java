import dbTools.DbUtil;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        DbUtil.getINSTANCE().dbConnection();


        int flag = -1;
        while (flag != 0) {

            if (flag != 1) {
                printMenu();
            }
            System.out.println("\n Please choose one from this list!");
            Scanner scanner = new Scanner(System.in);
            flag = scanner.nextInt();

            switch (flag) {
                case 1:
                    printMenu();
                    break;
                case 2:
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
                default:
                    printMenu();
                    break;
            }
        }

    }

    public static void printMenu() {
        System.out.println("----------Menü(1)-------------");
        System.out.println("--------Új felhasználó(2)------------");
    }

}
