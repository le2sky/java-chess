package chess.persistence.mysql;

import java.util.List;

interface BoardHistoryDao {

    void saveOne(BoardHistoryEntity entity);

    void deleteAll();

    List<BoardHistoryEntity> findAll();
}
