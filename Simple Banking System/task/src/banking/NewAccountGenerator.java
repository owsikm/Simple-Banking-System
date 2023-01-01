package banking;

import java.util.concurrent.ThreadLocalRandom;

public class NewAccountGenerator {

    public static Long cardNumber;
    public static int pin;
    public static String createAnAccount() {

        //Card number generator:
        long random = (long) Math.floor(Math.random() * 9_000_000_00L) + 1_000_000_00L;
        String concatCardNumber = "400000" + random;
        cardNumber = Long.valueOf(concatCardNumber);

        //Luhn Algorithm for checksum:
        String temp = Long.toString(cardNumber);
        Integer[] cardNumberArray = new Integer[temp.length()];
        int sum = 0;

        for (int i = 0; i < temp.length(); i++) {
            cardNumberArray[i] = ((temp.charAt(i) - '0'));

            if (i%2 == 0) {
                cardNumberArray[i] = cardNumberArray[i] * 2;
            }
            if (cardNumberArray[i] > 9) {
                cardNumberArray[i] = cardNumberArray[i] - 9;
            }
            sum = sum + cardNumberArray[i];
        }
        if (sum%10 != 0){
            temp = temp + (10 - sum % 10);
        } else temp = temp + (0);
        cardNumber = Long.valueOf(temp);

        //PIN generator:
        pin = ThreadLocalRandom.current().nextInt(1000,9999);

        //Save card number an PIN into DB:
        SQLiteConnection.createNewAccount(cardNumber, pin);

        return "Your card has been created\n" +
                "Your card number:\n" +
                cardNumber + "\n" +
                "Your card PIN:\n" +
                pin + "\n";
    }

    public static boolean luhnAlgorithmcheck(Long cardToTransferTo) {
        boolean luhncheck;

        //Luhn Algorithm for checksum:
        int lastDigitOfInputCardNumber = (int) Math.abs(cardToTransferTo % 10);
        String temp = Long.toString(cardToTransferTo);
        temp = temp.substring(0, temp.length() - 1);
        Integer[] cardNumberArray = new Integer[temp.length()];
        int sum = 0;

        for (int i = 0; i < temp.length(); i++) {
            cardNumberArray[i] = ((temp.charAt(i) - '0'));

            if (i % 2 == 0) {
                cardNumberArray[i] = cardNumberArray[i] * 2;
            }
            if (cardNumberArray[i] > 9) {
                cardNumberArray[i] = cardNumberArray[i] - 9;
            }
            sum = sum + cardNumberArray[i];
        }
        int lastDigitaccordingtoLuhn = ((10 - sum % 10));
        luhncheck = lastDigitaccordingtoLuhn == lastDigitOfInputCardNumber;
        return luhncheck;
    }
}
