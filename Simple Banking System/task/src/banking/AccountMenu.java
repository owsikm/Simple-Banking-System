package banking;

import java.util.Scanner;

import static banking.LogIntoAccount.inputCardNumber;

public class AccountMenu {

        public void accountMenu() {
        System.out.println("1. Balance\n" +
                "2. Add income\n" +
                "3. Do transfer\n" +
                "4. Close account\n" +
                "5. Log out\n" +
                "0. Exit");
        Scanner scan = new Scanner(System.in);
        int selection = scan.nextInt();
        int outputBalance = Integer.parseInt((SQLiteConnection.showBalance(inputCardNumber)));
        switch (selection) {
            case 1:
                System.out.println("Balance: " + outputBalance);
                break;
            case 2:
                System.out.println("Enter income:");
                int income = scan.nextInt();
                SQLiteConnection.updateBalance(income, inputCardNumber);
                System.out.println("Income was added!");
                break;
            case 3:
                System.out.println("Transfer\n" +
                        "Enter card number:");
                Long cardToTransferTo = scan.nextLong();
                if (NewAccountGenerator.luhnAlgorithmcheck(cardToTransferTo)) {
                    if (!(SQLiteConnection.ifCardExist(cardToTransferTo))) {
                        System.out.println("Such a card does not exist.");
                    } else {
                        System.out.println("Enter how much money you want to transfer:");
                        int amountToTransfer = scan.nextInt();
                        if (amountToTransfer > outputBalance) {
                            System.out.println("Not enough money!");
                        } else {
                            SQLiteConnection.transfer(inputCardNumber, cardToTransferTo, amountToTransfer);
                            System.out.println("Success!");
                        }
                    }
                } else {
                    System.out.println("Probably you made a mistake in the card number. Please try again!");
                }
                break;
            case 4:
                SQLiteConnection.deleteAccount(inputCardNumber);
                System.out.println("The account has been closed!");
                MainMenu.mainMenu();
                break;
            case 5:
                System.out.println("You have successfully logged out!");
                MainMenu.mainMenu();
                break;
            case 0:
                System.exit(0);
        }
        accountMenu();
    }
}
