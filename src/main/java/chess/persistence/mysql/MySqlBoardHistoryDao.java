package chess.persistence.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlBoardHistoryDao implements BoardHistoryDao {

    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "chess";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "password";

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
        return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
    }
}
