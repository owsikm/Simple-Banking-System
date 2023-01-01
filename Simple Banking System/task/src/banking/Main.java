package banking;

public class Main {
    public static String mainUrl="";

    public static void main(String[] args) {
        mainUrl = "jdbc:sqlite:" + args[1];
        SQLiteConnection.createDB();
    }
}