import java.sql.*;


class DBConDao {
    String dbURL = "jdbc:mysql://127.0.0.1:3306/tictactoe";
    String dbUSERNAME = "root";
    String dbPASSWORD = "Hello123";
    int playerwin = -1;
    int playerloss = -1;
    int points = -1;
    int playerID = 0;
    private static final String insertUserQuery = "INSERT INTO login (Username, Password) VALUES (?, ?)";
    private static final String insertPlayerData = "INSERT INTO playerdata (playerwins, playerloss, points) VALUES (?, ?, ?)";
    private static final String loginQuery = "SELECT * FROM login WHERE Username = ? and Password = ?";
    private static final String retrieveData = "SELECT * FROM playerdata WHERE playerID = ?";
    private static final String updateWin = "UPDATE playerdata SET playerwins = playerwins + ? WHERE playerID = ?";
    private static final String updateLoss = "UPDATE playerdata SET playerloss = playerloss + ? WHERE playerID = ?";
    private static final String updatePoint = "UPDATE playerdata SET points = points + ? WHERE playerID = ?";

    boolean insertUser(String username, String password) throws SQLException, ClassNotFoundException {
        try (Connection connection = DriverManager.getConnection(dbURL, dbUSERNAME, dbPASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertUserQuery);
             PreparedStatement preparedStatement2 = connection.prepareStatement(insertPlayerData);) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            preparedStatement2.setInt(1, 0);
            preparedStatement2.setInt(2, 0);
            preparedStatement2.setInt(3, 0);

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            preparedStatement2.executeUpdate();
        } catch (SQLException e) {
            // print SQL exception information
            System.out.print(e);
            return false;
        }
        return true;
    }

    boolean login(String username, String password) throws SQLException, ClassNotFoundException {
        try (Connection connection = DriverManager.getConnection(dbURL, dbUSERNAME, dbPASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(loginQuery);
             PreparedStatement preparedStatement2 = connection.prepareStatement(retrieveData)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                playerID = resultSet.getInt(1);
                preparedStatement2.setInt(1, playerID);
                ResultSet dataSet = preparedStatement2.executeQuery();
                dataSet.next();
                playerID = dataSet.getInt(1);
                playerwin = dataSet.getInt(2);
                playerloss = dataSet.getInt(3);
                points = dataSet.getInt(4);
                return true;
            }

        } catch (SQLException e) {
            System.out.print(e);
            return false;
        }
        return false;
    }

    public boolean updateStats(int win, int loss, int point) throws SQLException, ClassNotFoundException {
        try (Connection connection = DriverManager.getConnection(dbURL, dbUSERNAME, dbPASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(updateWin);
             PreparedStatement preparedStatement2 = connection.prepareStatement(updateLoss);
             PreparedStatement preparedStatement3 = connection.prepareStatement(updatePoint)) {

            preparedStatement.setInt(1, win);
            preparedStatement2.setInt(1, loss);
            preparedStatement3.setInt(1, point);

            preparedStatement.setInt(2, playerID);
            preparedStatement2.setInt(2, playerID);
            preparedStatement3.setInt(2, playerID);

            preparedStatement.executeUpdate();
            preparedStatement2.executeUpdate();
            preparedStatement3.executeUpdate();
        } catch (SQLException e) {
            System.out.print(e);
            return false;
        }
        return true;
    }
}