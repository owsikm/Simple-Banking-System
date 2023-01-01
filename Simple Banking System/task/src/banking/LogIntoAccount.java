package banking;

import java.util.Scanner;

public class LogIntoAccount {
public static Long inputCardNumber=0L;
    public static void logIntoAccount() {

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter your card number: ");
        inputCardNumber = scan.nextLong();
        System.out.println("Enter your PIN: ");
        int inputPin = scan.nextInt();
        String successfulLogin = SQLiteConnection.logIn(inputCardNumber, inputPin)[0];
        if (successfulLogin.equals("1")) {
                System.out.println("You have successfully logged in!");
                new AccountMenu().accountMenu();
        } else System.out.println("Wrong card number or PIN!");
    }
}
