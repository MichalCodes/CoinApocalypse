package lab;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
    public static void createDefaults(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS userdata (username VARCHAR(100) PRIMARY KEY, money INT NOT NULL, coinlvl INT NOT NULL, speedlvl INT NOT NULL, backgoroundlvl INT NOT NULL)"; //připraveno pro možné rozšíření multiuser
        String createTableSQL2 = "CREATE TABLE IF NOT EXISTS scores (id INT AUTO_INCREMENT PRIMARY KEY, score INT NOT NULL)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL)) {
            preparedStatement.executeUpdate();
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL2)) {
            preparedStatement.executeUpdate();
        }
    }
    public static void insertUser(Connection connection) throws SQLException {
        String insertDataSQL = "INSERT INTO userdata (username, money, coinlvl, speedlvl, backgoroundlvl) VALUES (?, ?, ?, ?, ?))";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertDataSQL)) {
            preparedStatement.setString(1, "User");
            preparedStatement.setInt(2, 100000);
            preparedStatement.setInt(3, 1);
            preparedStatement.setInt(4, 1);
            preparedStatement.setInt(5, 1);
            preparedStatement.executeUpdate();
        }
    }
    public static int find(Connection connection) throws SQLException {
        int number = 0;
        String selectDataSQL = "SELECT COUNT(*) AS Pocet FROM userdata WHERE username = 'User'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectDataSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                number = resultSet.getInt("Pocet");
            }
        }
        return number;
    }
    public static void insertScore(Connection connection, int score) throws SQLException {
        String insertDataSQL = "INSERT INTO scores (score) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertDataSQL)) {
            preparedStatement.setInt(1, score);
            preparedStatement.executeUpdate();
        }
    }
    public static int[] selectScores(Connection connection) throws SQLException {
        int []numbers = new int[5];
        String selectDataSQL = "SELECT score FROM scores ORDER BY score DESC";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectDataSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            int i = 0;
            while (resultSet.next() && i < 5) {
                numbers[i] = resultSet.getInt("score");
                i++;
            }
        }
        return numbers;
    }
    public static int[] selectUserUpdates(Connection connection) throws SQLException {
        int[] numbers = new int[3];
        String selectDataSQL = "SELECT coinlvl, speedlvl, backgoroundlvl FROM userdata WHERE username = 'User'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectDataSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                numbers[0] = resultSet.getInt("coinlvl");
                numbers[1] = resultSet.getInt("speedlvl");
                numbers[2] = resultSet.getInt("backgoroundlvl");
            }
        }
        return numbers;
    }
    public static int selectUserCoins(Connection connection) throws SQLException {
        int number = 0;
        String selectDataSQL = "SELECT money FROM userdata WHERE username = 'User'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectDataSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                number = resultSet.getInt("money");
            }
        }
        return number;
    }
    public static void updateUserCoins(Connection connection, int coins) throws SQLException {
        String updateDataSQL = "UPDATE userdata SET money = ? WHERE username = 'User'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateDataSQL)) {
            preparedStatement.setInt(1, coins);
            preparedStatement.executeUpdate();
        }
    }
    public static void updateUserlvls(Connection connection, int coinlvl, int speedlvl, int backgroundlvl) throws SQLException {
        String updateDataSQL = "UPDATE userdata SET coinlvl = ?, speedlvl = ?, backgoroundlvl = ? WHERE username = 'User'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateDataSQL)) {
            preparedStatement.setInt(1, coinlvl);
            preparedStatement.setInt(2, speedlvl);
            preparedStatement.setInt(3, backgroundlvl);
            preparedStatement.executeUpdate();
        }
    }
    static void deleteData(Connection connection, String name) throws SQLException {
        String deleteDataSQL = "DELETE FROM score WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteDataSQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
        }
    }
}
