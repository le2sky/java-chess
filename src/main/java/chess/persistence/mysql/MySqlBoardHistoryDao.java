package chess.persistence.mysql;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MySqlBoardHistoryDao implements BoardHistoryDao {

    private final String url;
    private final String username;
    private final String password;

    public MySqlBoardHistoryDao() {
        String path = "src/main/java/resources/application.properties";
        try (FileInputStream fileInputStream = new FileInputStream(path)) {
            Properties properties = new Properties();
            properties.load(fileInputStream);
            this.url = createUrl(properties);
            this.username = properties.getProperty("username");
            this.password = properties.getProperty("password");
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private String createUrl(Properties properties) {
        String server = properties.getProperty("server");
        String database = properties.getProperty("database");
        String option = properties.getProperty("option");

        return "jdbc:mysql://" + server + "/" + database + option;
    }

    @Override
    public void saveOne(BoardHistoryEntity entity) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("insert into board_history values (0, ?, ?, ?, ?)");
            preparedStatement.setInt(1, entity.sourceRank);
            preparedStatement.setString(2, String.valueOf(entity.sourceFile));
            preparedStatement.setInt(3, entity.targetRank);
            preparedStatement.setString(4, String.valueOf(entity.targetFile));
            preparedStatement.execute();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void deleteAll() {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from board_history");
            preparedStatement.execute();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public List<BoardHistoryEntity> findAll() {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from board_history");
            ResultSet resultSet = preparedStatement.executeQuery();
            return createHistories(resultSet);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    private List<BoardHistoryEntity> createHistories(ResultSet resultSet) throws SQLException {
        List<BoardHistoryEntity> histories = new ArrayList<>();
        while (resultSet.next()) {
            int sourceRank = resultSet.getInt("source_rank");
            char sourceFile = resultSet.getString("source_file").charAt(0);
            int targetRank = resultSet.getInt("target_rank");
            char targetFile = resultSet.getString("target_file").charAt(0);
            histories.add(new BoardHistoryEntity(sourceRank, sourceFile, targetRank, targetFile));
        }
        return histories;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
