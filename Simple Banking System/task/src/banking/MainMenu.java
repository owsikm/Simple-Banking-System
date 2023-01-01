package banking;

import java.util.Scanner;

public class MainMenu {
    public static void mainMenu() {
        System.out.println("1. Create an account\n" +
                "2. Log into account\n" +
                "0. Exit");
        Scanner scan = new Scanner(System.in);
        int selection = scan.nextInt();
        System.out.println();
        switch (selection) {
            case 1:
                System.out.println(NewAccountGenerator.createAnAccount());
                break;
            case 2:
                LogIntoAccount.logIntoAccount();
                break;
            case 0:
                System.exit(0);
        }
        mainMenu();
    }
}
