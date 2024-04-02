package chess.persistence.mysql;

import java.util.List;

class SimpleBoardHistoryDao implements BoardHistoryDao {

    private final List<BoardHistoryEntity> entities;

    public SimpleBoardHistoryDao(List<BoardHistoryEntity> entities) {
        this.entities = entities;
    }

    @Override
    public void saveOne(BoardHistoryEntity entity) {
        entities.add(entity);
    }

    @Override
    public void deleteAll() {
        entities.clear();
    }

    @Override
    public List<BoardHistoryEntity> findAll() {
        return entities;
    }
}
