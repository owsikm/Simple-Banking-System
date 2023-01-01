package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.*;


public class SQLiteConnection {
    static String successfulLogin = "";
    static String dbUrl = Main.mainUrl;
    static String url = dbUrl;
    static SQLiteDataSource dataSource = new SQLiteDataSource();

static void createDB(){

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()) {
            try (Statement statement = con.createStatement()) {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS card(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "number TEXT NOT NULL," +
                        "pin TEXT NOT NULL," +
                        "balance INTEGER DEFAULT 0)");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        MainMenu.mainMenu();
    }

    public static void createNewAccount(Long cardNumber, int pin) {
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()) {
            try (Statement statement = con.createStatement()) {
                statement.executeUpdate(
                        "INSERT INTO card (number, pin)" +
                                "VALUES (" + cardNumber + ", " + pin + ");"
                );
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static String[] logIn(Long inputCardNumber, int inputPin) {

        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()) {
            try (Statement statement = con.createStatement()) {
                try (ResultSet cardsearch = statement.executeQuery(

                        "SELECT number, pin, balance FROM card where number="
                                + inputCardNumber + " and pin= " + inputPin + " ")) {
                    if (cardsearch.isBeforeFirst()) {
                        successfulLogin = "1";
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new String[]{successfulLogin};
    }

    public static void updateBalance(int income, Long inputCardNumber) {
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()) {
            String insert = "UPDATE card SET balance = balance + ? WHERE number = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(insert)) {
                preparedStatement.setObject(1, income);
                preparedStatement.setObject(2, inputCardNumber);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String showBalance(Long inputCardNumber) {
        String displayBalance = null;
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()) {
            String select = "select balance from card WHERE number = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(select)) {
                preparedStatement.setObject(1, inputCardNumber);
                displayBalance = preparedStatement.executeQuery().getString("balance");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return displayBalance;
    }

    static void deleteAccount(Long inputCardNumber) {
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()) {
            String delete = "DELETE FROM card WHERE number = ? ";
            try (PreparedStatement preparedStatement = con.prepareStatement(delete)) {
                preparedStatement.setObject(1, inputCardNumber);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void transfer(Long inputCardNumber, Long cardToTransferTo, int amountToTransfer) {
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()) {
            con.setAutoCommit(false);

            String takeMoney = "UPDATE card SET balance = balance - ? WHERE number = ?";
            String placeMoney = "UPDATE card SET balance = balance + ? WHERE number = ?";
            try (PreparedStatement take = con.prepareStatement(takeMoney);
                 PreparedStatement place = con.prepareStatement(placeMoney)) {

                take.setObject(1, amountToTransfer);
                take.setObject(2, inputCardNumber);
                take.executeUpdate();

                place.setObject(1, amountToTransfer);
                place.setObject(2, cardToTransferTo);
                place.executeUpdate();

                con.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean ifCardExist(Long cardToTransferTo) {
        boolean cardExist = false;
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()) {
            String select = "select id from card WHERE number = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(select)) {
                preparedStatement.setObject(1, cardToTransferTo);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.isBeforeFirst()) {
                    cardExist = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cardExist;
    }
}
