package chess.persistence.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardHistoryDao {

    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "chess";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public void saveOne(BoardHistoryEntity entity) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("insert into board_histories values (0, ?, ?, ?, ?)");
            preparedStatement.setInt(1, entity.sourceRank);
            preparedStatement.setInt(2, entity.sourceFile);
            preparedStatement.setInt(3, entity.targetRank);
            preparedStatement.setInt(4, entity.targetFile);
            preparedStatement.execute();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void deleteAll() {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from board_histories");
            preparedStatement.execute();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public List<BoardHistoryEntity> findAll() {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from board_histories");
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
            int sourceFile = resultSet.getInt("source_file");
            int targetRank = resultSet.getInt("target_rank");
            int targetFile = resultSet.getInt("target_file");
            histories.add(new BoardHistoryEntity(sourceRank, sourceFile, targetRank, targetFile));
        }
        return histories;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
    }
}
